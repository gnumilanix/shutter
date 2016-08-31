package com.milanix.shutter.auth;

import com.milanix.shutter.core.AbstractPresenter;

/**
 * Created by milan on 30/8/2016.
 */

public class AuthPresenter extends AbstractPresenter<AuthContract.View> implements AuthContract.Presenter{
    public AuthPresenter(AuthContract.View view) {
        super(view);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loginWithGoogle() {

    }

    @Override
    public void loginWithFacebook() {

    }
}
