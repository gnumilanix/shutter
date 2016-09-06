package com.milanix.shutter.user.profile.followings;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.user.model.Profile;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface FollowingListContract {
    interface View extends IView {
        void openProfile(android.view.View view, String profileId);

        void handleUnfollowError(Profile profile);

        void handleFollowError(Profile profile);
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void unfollow(Profile user);

        void follow(Profile user);
    }
}
