package com.milanix.shutter.user.profile.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Profile presenter
 *
 * @author milan
 */
public class ProfileDetailPresenter extends AbstractPresenter<ProfileDetailContract.View> implements ProfileDetailContract.Presenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final App app;
    private final FirebaseUser user;
    private final FirebaseAuth auth;
    private final GoogleApiClient googleApi;
    private final String profileId;
    private final Query postsQuery;
    private final DatabaseReference profileReference;

    @Inject
    public ProfileDetailPresenter(ProfileDetailContract.View view, App app, FirebaseUser user, FirebaseAuth auth,
                                  FirebaseDatabase database, GoogleSignInOptions googleSignInOptions,
                                  @Named(ProfileModule.PROFILE_ID) String profileId) {
        super(view);
        this.app = app;
        this.user = user;
        this.auth = auth;
        this.googleApi = new GoogleApiClient.Builder(app)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        this.profileId = profileId;
        this.profileReference = database.getReference().child("users").child(profileId);
        this.postsQuery = database.getReference().child("posts").orderByChild("authorId").equalTo(profileId);
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        super.subscribe();
        postsQuery.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        super.unsubscribe();
        postsQuery.removeEventListener(childEventListener);
    }

    @Override
    public void refreshPosts() {
        view.hideProgress();
    }

    @Override
    public void subscribe() {
        if (!googleApi.isConnected() || !googleApi.isConnecting())
            googleApi.connect();

        googleApi.registerConnectionCallbacks(this);
        googleApi.registerConnectionFailedListener(this);
    }

    @Override
    public void unsubscribe() {
        googleApi.unregisterConnectionCallbacks(this);
        googleApi.unregisterConnectionFailedListener(this);
    }

    @Override
    public void getProfile() {
        profileReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);

                if (isActive()) {
                    if (null != profile) {
                        view.setProfile(profile);
                    } else {
                        view.handleProfileRefreshError();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (isActive()) {
                    view.handleProfileRefreshError();
                }
            }
        });
    }

    @Override
    public void logout() {
        auth.signOut();
        logoutFromFacebook();
        logoutFromGoogle();
    }

    @Override
    public boolean isCurrentUserProfile() {
        return profileId.equals(user.getUid());
    }

    private void logoutFromGoogle() {
        if (googleApi.isConnected()) {
            Auth.GoogleSignInApi.signOut(googleApi).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            finishLogout();
                        }
                    });
        } else {
            finishLogout();
        }
    }

    private void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
        finishLogout();
    }

    private void finishLogout() {
        app.releaseUserComponent();

        if (isActive()) {
            view.logoutComplete();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
