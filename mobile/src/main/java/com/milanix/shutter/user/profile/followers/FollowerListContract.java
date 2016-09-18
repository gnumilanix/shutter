package com.milanix.shutter.user.profile.followers;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.user.model.Profile;

/**
 * Contract defining follower list related view/presenter implementations
 *
 * @author milan
 */
public interface FollowerListContract {
    interface View extends IView {
        void openProfile(android.view.View view, String profileId);

        void handleUnfollowError(Profile profile);

        void handleFollowError(Profile profile);
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener followingChangesListener, ValueEventListener profileChangeListener);

        void unsubscribe(ChildEventListener followingChangesListener, ValueEventListener profileChangeListener);

        void unfollow(Profile user);

        void follow(Profile user);
    }
}
