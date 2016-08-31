package com.milanix.shutter.auth.resetpassword;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface RequestPasswordContract {
    interface View extends IView {
        void handleResetPasswordFailure();

        void completeRequestPassword(String email);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void requestPassword(RequestPassword requestPassword);
    }
}
