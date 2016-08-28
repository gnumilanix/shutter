package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.feed.model.IFeedRepository;
import com.milanix.shutter.user.model.IUserRepository;
import com.milanix.shutter.user.model.User;

import java.util.List;

import javax.inject.Inject;

/**
 * Profile presenter
 *
 * @author milan
 */
public class ProfilePresenter extends AbstractPresenter<ProfileContract.View> implements ProfileContract.Presenter {
    private final IStore.Callback<User> userCallback = new IStore.Callback<User>() {
        @Override
        public void onSuccess(User result) {
            view.hideProgress();
            view.showProfile(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.hideProgress();
            view.handleProfileRefreshError();
        }
    };
    private final IStore.Callback<List<Feed>> postsCallback = new IStore.Callback<List<Feed>>() {
        @Override
        public void onSuccess(List<Feed> result) {
            view.hideProgress();
            view.showPosts(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.hideProgress();
            view.handleProfileRefreshError();
        }
    };
    private final IUserRepository userRepository;
    private final IFeedRepository feedRepository;

    @Inject
    public ProfilePresenter(ProfileContract.View view, IUserRepository userRepository, IFeedRepository feedRepository) {
        super(view);
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void getProfile() {
        view.showProgress();
        userRepository.getSelf(userCallback);
        feedRepository.getFeeds(postsCallback);
    }

    @Override
    public void refreshProfile() {
        view.showProgress();
        userRepository.refreshSelf(userCallback);
        feedRepository.refreshFeeds(postsCallback);
    }
}
