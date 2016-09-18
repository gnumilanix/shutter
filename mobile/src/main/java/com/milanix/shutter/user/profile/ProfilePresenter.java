package com.milanix.shutter.user.profile;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.notification.NotificationGenerator;
import com.milanix.shutter.notification.model.Notification;
import com.milanix.shutter.user.model.Profile;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * Profile presenter
 *
 * @author milan
 */
public class ProfilePresenter extends AbstractPresenter<ProfileContract.View> implements ProfileContract.Presenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ValueEventListener {
    private final App app;
    private final FirebaseUser user;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final NotificationGenerator notificationGenerator;
    private final GoogleApiClient googleApi;
    private final String profileId;
    private final DatabaseReference profileReference;

    @Inject
    public ProfilePresenter(ProfileContract.View view, App app, FirebaseUser user, FirebaseAuth auth,
                            FirebaseDatabase database, GoogleSignInOptions googleSignInOptions,
                            NotificationGenerator notificationGenerator,
                            @Named(ProfileModule.PROFILE_ID) String profileId) {
        super(view);
        this.app = app;
        this.user = user;
        this.auth = auth;
        this.database = database;
        this.notificationGenerator = notificationGenerator;
        this.googleApi = new GoogleApiClient.Builder(app)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        this.profileId = profileId;
        this.profileReference = database.getReference().child("users").child(profileId);
    }

    @Override
    public void subscribe() {
        if (!googleApi.isConnected() || !googleApi.isConnecting())
            googleApi.connect();

        googleApi.registerConnectionCallbacks(this);
        googleApi.registerConnectionFailedListener(this);
        profileReference.addValueEventListener(this);
    }

    @Override
    public void unsubscribe() {
        googleApi.unregisterConnectionCallbacks(this);
        googleApi.unregisterConnectionFailedListener(this);
        profileReference.removeEventListener(this);
    }

    @Override
    public void logout() {
        auth.signOut();
        logoutFromFacebook();
        logoutFromGoogle();
    }

    @Override
    public FirebaseUser getMe() {
        return user;
    }

    @Override
    public boolean isMe() {
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
        app.initializeFacebookSDK();
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

    }

    @Override
    public void toggleFollow() {
        database.getReference().child("users").child(user.getUid()).child("followings").child(profileId).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            unfollow();
                        else
                            follow();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.handleToggleFollowError();
                    }
                });

    }

    private void follow() {
        final Map<String, Object> update = new HashMap<>();
        update.put("/users/" + profileId + "/followers/" + user.getUid(), true);
        update.put("/users/" + user.getUid() + "/followings/" + profileId, true);
        update.putAll(notificationGenerator.generate(Notification.Type.FOLLOW, profileId, null));

        database.getReference().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Timber.d("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.d("failure");
            }
        });
    }

    private void unfollow() {
        final Map<String, Object> update = new HashMap<>();
        update.put("/users/" + profileId + "/followers/" + user.getUid(), null);
        update.put("/users/" + user.getUid() + "/followings/" + profileId, null);
        update.putAll(notificationGenerator.generate(Notification.Type.UNFOLLOW, profileId, null));

        database.getReference().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Timber.d("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.d("failure");
            }
        });
    }

}
