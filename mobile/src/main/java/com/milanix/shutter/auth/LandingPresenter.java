package com.milanix.shutter.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.model.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//// TODO: 1/9/2016 comment this
public class LandingPresenter extends AbstractPresenter<LandingContract.View> implements LandingContract.Presenter,
        FirebaseAuth.AuthStateListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final int RC_GOOGLE_LOG_IN = 100;
    public static final List<String> PERMISSIONS_FACEBOOK = Arrays.asList("public_profile", "email");

    private final App app;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private CallbackManager facebookCallbackManager;
    private GoogleApiClient googleApi;
    private FacebookCallback<LoginResult> facebookCallback;

    public LandingPresenter(LandingContract.View view, App app, FirebaseAuth auth, FirebaseDatabase database,
                            GoogleSignInOptions googleSignInOptions) {
        super(view);
        this.app = app;
        this.auth = auth;
        this.database = database;
        this.googleApi = new GoogleApiClient.Builder(app)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

    }

    @Override
    public void subscribe() {
        auth.addAuthStateListener(this);

        initGoogleAPi();
        initFacebookApi();
        googleApi.registerConnectionCallbacks(this);
        googleApi.registerConnectionFailedListener(this);
        LoginManager.getInstance().registerCallback(facebookCallbackManager, facebookCallback);
    }

    private void initGoogleAPi() {
        if (!googleApi.isConnected() || !googleApi.isConnecting())
            googleApi.connect();
    }

    private void initFacebookApi() {
        if (null == facebookCallbackManager) {
            this.facebookCallbackManager = CallbackManager.Factory.create();
            this.facebookCallback = new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookLoginResult(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    handleLoginError();
                }

                @Override
                public void onError(FacebookException error) {
                    handleLoginError();
                }
            };
        }
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        googleApi.unregisterConnectionCallbacks(this);
        googleApi.unregisterConnectionFailedListener(this);
        auth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (isActive()) {
            view.handleGoogleApiFailure();
        }
    }

    @Override
    public void loginWithGoogle() {
        if (isActive()) {
            if (googleApi.isConnected()) {
                view.showProgress();
                view.loginWithGoogle(Auth.GoogleSignInApi.getSignInIntent(googleApi), RC_GOOGLE_LOG_IN);
            } else {
                view.handleGoogleApiFailure();
            }
        }
    }

    @Override
    public void loginWithFacebook() {
        if (isActive()) {
            view.showProgress();
            view.loginWithFacebook(PERMISSIONS_FACEBOOK);
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_GOOGLE_LOG_IN) {
            handleGoogleLoginResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        } else {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void handleGoogleLoginResult(GoogleSignInResult result) {
        try {
            if (result.isSuccess()) {
                loginWithCredential(GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null));
            } else {
                handleLoginError();
            }
        } catch (Exception e) {
            handleLoginError();
        }
    }

    private void handleFacebookLoginResult(AccessToken accessToken) {
        try {
            loginWithCredential(FacebookAuthProvider.getCredential(accessToken.getToken()));
        } catch (Exception e) {
            handleLoginError();
        }
    }

    private void loginWithCredential(AuthCredential credential) {
        auth.signInWithCredential(credential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (isActive()) {
                            if (task.isSuccessful()) {
                                createProfile(task.getResult().getUser());
                            } else {
                                handleLoginError();
                            }
                        }
                    }
                });
    }

    private void createProfile(final FirebaseUser user) {
        final UserInfo providerData = user.getProviderData().get(0);
        final String avatarUrl = null == providerData.getPhotoUrl() ? null : providerData.getPhotoUrl().toString();
        final Profile profile = new Profile(user.getUid(), user.getEmail(), avatarUrl, providerData.getDisplayName(), null, null, null);
        final Map<String, Object> updates = new HashMap<>();
        updates.put("/users/" + user.getUid(), profile.toCreateProfileMap());

        database.getReference().updateChildren(updates).
                continueWith(new Continuation<Void, Void>() {
                    @Override
                    public Void then(@NonNull Task<Void> task) throws Exception {
                        updateProfile(user, providerData);

                        return null;
                    }
                });
    }

    private void updateProfile(FirebaseUser user, UserInfo providerData) {
        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(providerData.getDisplayName())
                .setPhotoUri(providerData.getPhotoUrl())
                .build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                completeSignUp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                completeSignUp();
            }
        });
    }

    private void completeSignUp() {
        if (isActive()) {
            app.createUserComponent(auth.getCurrentUser());
            view.completeLogin();
        }
    }

    private void handleLoginError() {
        if (isActive()) {
            view.hideProgress();
            view.handleLoginError();
        }
    }
}
