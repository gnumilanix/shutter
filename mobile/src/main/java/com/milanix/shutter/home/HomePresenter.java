package com.milanix.shutter.home;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.AbstractPresenter;

/**
 * Home presenter
 *
 * @author milan
 */
public class HomePresenter extends AbstractPresenter<HomeContract.View> implements HomeContract.Presenter {
    private final FirebaseUser user;

    public HomePresenter(HomeContract.View view, FirebaseUser user) {
        super(view);
        this.user = user;
    }

    @Override
    public void getUser() {
        view.setUser(user);
    }
}
