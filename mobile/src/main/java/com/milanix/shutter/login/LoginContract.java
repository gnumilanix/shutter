package com.milanix.shutter.login;

import com.android.annotations.NonNull;
import com.milanix.shutter.login.model.Login;
import com.milanix.shutter.specs.IPresenter;
import com.milanix.shutter.specs.IView;

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
    }

    interface Presenter extends IPresenter {
        void login(@NonNull Login login);
    }
}
