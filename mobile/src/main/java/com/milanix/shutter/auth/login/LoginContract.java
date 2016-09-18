package com.milanix.shutter.auth.login;

import android.support.annotation.NonNull;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for login related implementations
 *
 * @author milan
 */
public interface LoginContract {
    interface View extends IView {
        void handleInvalidUsername();

        void handleInvalidPassword();

        void handleInvalidLogin();

        void setSessionAvailable();

        void showProgress();

        void hideProgress();

    }

    interface Presenter extends IPresenter {
        void login(@NonNull LoginModel login);

        void requestPassword(String email);
    }
}
