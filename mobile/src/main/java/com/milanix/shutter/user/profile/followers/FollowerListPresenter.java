package com.milanix.shutter.user.profile.followers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    private FirebaseDatabase database;
    private final Query followingsQuery;

    @Inject
    public FollowerListPresenter(FollowerListContract.View view, FirebaseUser user,
                                 FirebaseDatabase database, @Named(ProfileModule.PROFILE_ID) String profileId) {
        super(view);
        this.user = user;
        this.database = database;
        this.followingsQuery = database.getReference().child("users").orderByChild("followers/" + profileId + "/").equalTo(true);
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        followingsQuery.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        followingsQuery.removeEventListener(childEventListener);
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
        final Map<String, Object> followerValue = new HashMap<>();
        followerValue.put(user.getUid(), true);

        final Map<String, Object> followingValue = new HashMap<>();
        followingValue.put(profile.userId, true);

        final Map<String, Object> update = new HashMap<>();
        update.put("/users/" + profile.userId + "/followers/", followerValue);
        update.put("/users/" + user.getUid() + "/followings/", followingValue);

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
