package com.milanix.shutter.home;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;
import com.milanix.shutter.user.model.IUserRepository;
import com.milanix.shutter.user.model.User;

/**
 * Home presenter
 *
 * @author milan
 */
public class HomePresenter extends AbstractPresenter<HomeContract.View> implements HomeContract.Presenter {
    private final IUserRepository repository;

    public HomePresenter(HomeContract.View view, IUserRepository repository) {
        super(view);
        this.repository = repository;
    }

    @Override
    public void getUser() {
        repository.getSelf(new IStore.Callback<User>() {
            @Override
            public void onSuccess(User result) {
                view.setUser(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void subscribe() {

    }
}
