package com.milanix.shutter.login;

import com.android.annotations.NonNull;
import com.milanix.shutter.core.IPresenter;
import com.milanix.shutter.core.IView;
import com.milanix.shutter.login.model.Login;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface LoginContract {
    interface View extends IView {
        void handleInvalidPassword();

        void handleInvalidLogin();

        void setSessionAvailable();

        void setSessionUnavailable();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void login(@NonNull Login login);
    }
}
