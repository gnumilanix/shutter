package com.milanix.shutter.user.profile;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.user.model.Profile;

/**
 * Contract defining profile related view/presenter implementations
 *
 * @author milan
 */
public interface ProfileContract {
    interface View extends IView {
        void viewPosts();

        void viewFollowings();

        void viewFollowers();

        void setProfile(Profile profile);

        void showProgress();

        void hideProgress();

        void logoutComplete();

        void handleProfileRefreshError();

        void handleToggleFollowError();
    }

    interface Presenter extends IPresenter {
        void logout();

        void toggleFollow();

        FirebaseUser getMe();

        boolean isMe();
    }
}
