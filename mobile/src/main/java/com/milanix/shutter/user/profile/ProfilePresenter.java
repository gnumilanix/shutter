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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.user.model.Profile;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Profile presenter
 *
 * @author milan
 */
public class ProfilePresenter extends AbstractPresenter<ProfileContract.View> implements ProfileContract.Presenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final App app;
    private final FirebaseUser user;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final GoogleApiClient googleApi;

    @Inject
    public ProfilePresenter(ProfileContract.View view, App app, FirebaseUser user, FirebaseAuth auth,
                            FirebaseDatabase database, GoogleSignInOptions googleSignInOptions) {
        super(view);
        this.app = app;
        this.user = user;
        this.auth = auth;
        this.database = database;
        this.googleApi = new GoogleApiClient.Builder(app)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
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
        database.getReference().child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void getPosts() {
        database.getReference().child("posts").orderByChild("authorId").equalTo(user.getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<Post> posts = new ArrayList<>();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            posts.add(postSnapshot.getValue(Post.class));
                        }

                        if (isActive()) {
                            view.showPosts(posts);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (isActive()) {
                            view.handlePostRefreshError();
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
        if (user.getProviderId().equals(FacebookAuthProvider.PROVIDER_ID)) {
            LoginManager.getInstance().logOut();
            finishLogout();
        }
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
