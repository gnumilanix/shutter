package com.milanix.shutter.user.profile.detail;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.user.model.Profile;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface ProfileDetailContract {
    interface View extends IView {
        void setProfile(Profile profile);

        void openPost(String postId);

        void handleProfileRefreshError();

        void showProgress();

        void hideProgress();

        void logoutComplete();

        void toggleFollow();

        void handleToggleFollowError();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshPosts();

        void toggleFollow();

        void logout();

        FirebaseUser getMe();

        boolean isMyProfile();
    }
}
