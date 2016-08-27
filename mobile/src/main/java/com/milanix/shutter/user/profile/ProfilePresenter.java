package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;
import com.milanix.shutter.user.model.IUserRepository;
import com.milanix.shutter.user.model.User;

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
            view.showProfile(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.handleProfileRefreshError();
        }
    };
    private final IUserRepository repository;

    @Inject
    public ProfilePresenter(ProfileContract.View view, IUserRepository repository) {
        super(view);
        this.repository = repository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void getProfile() {
        repository.getSelf(userCallback);
    }

    @Override
    public void refreshProfile() {
        repository.refreshSelf(userCallback);
    }
}
