package com.milanix.shutter.user.profile.detail;

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
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshPosts();

        void getProfile();

        void logout();

        boolean isCurrentUserProfile();
    }
}
