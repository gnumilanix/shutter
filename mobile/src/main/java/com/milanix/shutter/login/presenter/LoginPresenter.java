package com.milanix.shutter.login.presenter;

import com.milanix.shutter.App;
import com.milanix.shutter.login.LoginContract;
import com.milanix.shutter.login.model.Login;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.user.model.User;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Login presenter
 *
 * @author milan
 */
public class LoginPresenter extends AbstractPresenter<LoginContract.View> implements LoginContract.Presenter {

    private final App app;

    @Inject
    public LoginPresenter(LoginContract.View view, App app) {
        super(view);
        this.app = app;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void login(Login login) {
        app.createUserComponent(new User(10L, "John", "john.doe@example.com", UUID.randomUUID().toString()));
        view.setSessionAvailable();
    }
}
