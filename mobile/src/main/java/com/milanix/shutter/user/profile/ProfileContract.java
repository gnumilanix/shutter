package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.user.model.Profile;

import java.util.List;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface ProfileContract {
    interface View extends IView {
        void setProfile(Profile profile);

        void showPosts(List<Post> posts);

        void openPost(String postId);

        void handleProfileRefreshError();

        void handlePostRefreshError();

        void showProgress();

        void hideProgress();

        void logoutComplete();
    }

    interface Presenter extends IPresenter {
        void getProfile();

        void getPosts();

        void logout();
    }
}
