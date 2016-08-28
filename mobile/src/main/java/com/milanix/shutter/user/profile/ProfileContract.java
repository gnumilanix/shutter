package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.IPresenter;
import com.milanix.shutter.core.IView;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.user.model.User;

import java.util.List;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface ProfileContract {
    interface View extends IView {
        void showProfile(User user);

        void showPosts(List<Feed> posts);

        void openPost(long postId);

        void handleProfileRefreshError();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void getProfile();

        void refreshProfile();
    }
}
