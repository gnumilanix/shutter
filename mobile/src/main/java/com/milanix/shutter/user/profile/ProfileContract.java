package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.feed.model.Profile;

import java.util.List;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface ProfileContract {
    interface View extends IView {
        void setProfile(Profile profile);

        void showPosts(List<Feed> posts);

        void openPost(long postId);

        void handleProfileRefreshError();

        void showProgress();

        void hideProgress();

        void logoutComplete();
    }

    interface Presenter extends IPresenter {
        void getProfile();

        void refreshProfile();

        void logout();
    }
}
