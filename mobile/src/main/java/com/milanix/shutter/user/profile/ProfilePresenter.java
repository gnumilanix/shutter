package com.milanix.shutter.user.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.model.Profile;

import javax.inject.Inject;

/**
 * Profile presenter
 *
 * @author milan
 */
public class ProfilePresenter extends AbstractPresenter<ProfileContract.View> implements ProfileContract.Presenter {
    private final App app;
    private final FirebaseUser user;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;

    @Inject
    public ProfilePresenter(ProfileContract.View view, App app, FirebaseUser user, FirebaseAuth auth,
                            FirebaseDatabase database) {
        super(view);
        this.app = app;
        this.user = user;
        this.auth = auth;
        this.database = database;
    }

    @Override
    public void subscribe() {

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
                        view.hideProgress();
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
    public void refreshProfile() {
        getProfile();
    }

    @Override
    public void logout() {
        auth.signOut();
        app.releaseUserComponent();

        if (isActive()) {
            view.logoutComplete();
        }
    }
}
