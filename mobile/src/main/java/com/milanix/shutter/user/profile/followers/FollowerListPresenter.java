package com.milanix.shutter.user.profile.followers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.ProfileModule;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Profile presenter
 *
 * @author milan
 */
public class FollowerListPresenter extends AbstractPresenter<FollowerListContract.View> implements
        FollowerListContract.Presenter {
    private final FirebaseUser user;
    private final DatabaseReference profileReference;
    private final FirebaseDatabase database;
    private final Query followingsQuery;

    @Inject
    public FollowerListPresenter(FollowerListContract.View view, FirebaseUser user,
                                 FirebaseDatabase database, @Named(ProfileModule.PROFILE_ID) String profileId) {
        super(view);
        this.user = user;
        this.database = database;
        this.profileReference = database.getReference().child("users").child(user.getUid());
        this.followingsQuery = database.getReference().child("users").orderByChild("followings/" + profileId + "/").equalTo(true);
    }

    @Override
    public void subscribe(ChildEventListener followingChangesListener, ValueEventListener profileChangeListener) {
        followingsQuery.addChildEventListener(followingChangesListener);
        profileReference.addValueEventListener(profileChangeListener);
    }

    @Override
    public void unsubscribe(ChildEventListener followingChangesListener, ValueEventListener profileChangeListener) {
        followingsQuery.removeEventListener(followingChangesListener);
        profileReference.removeEventListener(profileChangeListener);
    }

    @Override
    public void unfollow(final Profile profile) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/users/" + profile.userId + "/followers/" + user.getUid(), null);
        update.put("/users/" + user.getUid() + "/followings/" + profile.userId, null);

        database.getReference().updateChildren(update).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isActive()) {
                    view.handleUnfollowError(profile);
                }
            }
        });
    }

    @Override
    public void follow(final Profile profile) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/users/" + profile.userId + "/followers/" + user.getUid(), true);
        update.put("/users/" + user.getUid() + "/followings/" + profile.userId, true);

        database.getReference().updateChildren(update).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isActive()) {
                    view.handleFollowError(profile);
                }
            }
        });
    }
}
