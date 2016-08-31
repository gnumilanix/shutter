package com.milanix.shutter.auth.signup;

import android.support.annotation.NonNull;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface SignUpContract {
    interface View extends IView {
        void handleExistingUser();

        void handleSignUpFailure();

        void handleUploadAvatarFailure();

        void completeSignUp();

        void selectAvatar();

        void openAgreement();

        void showProgress();

        void hideProgress();

        void passwordResetEmailSent();

        void handleResetPasswordError();

        void handleLoginFailure();
    }

    interface Presenter extends IPresenter {
        void signUp(@NonNull SignUp signUp);

        void requestPassword(String email);
    }
}
