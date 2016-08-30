package com.milanix.shutter.login;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for auth related implementations
 *
 * @author milan
 */
public interface AuthContract {
    interface View extends IView {
        void login();

        void signUp();

        void updateProfile();

        void completeLogin();

        void handleLoginError();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void loginWithGoogle();

        void loginWithFacebook();
    }
}
