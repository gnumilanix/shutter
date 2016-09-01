package com.milanix.shutter.auth.login;

import android.support.annotation.NonNull;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface LoginContract {
    interface View extends IView {
        void handleInvalidUsername();

        void handleInvalidPassword();

        void handleInvalidLogin();

        void setSessionAvailable();

        void setSessionUnavailable();

        void showProgress();

        void hideProgress();

    }

    interface Presenter extends IPresenter {
        void login(@NonNull Login login);

        void requestPassword(String email);
    }
}