package com.milanix.shutter.auth.signup;

import android.support.annotation.NonNull;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for sign up  related implementations
 *
 * @author milan
 */
public interface SignUpContract {
    interface View extends IView {

        void handleSignUpFailure();

        void handleUploadAvatarFailure();

        void completeSignUp();

        void selectAvatar();

        void openTerms();

        void openLogin();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void signUp(@NonNull SignUpModel signUp);

        String getTermsUrl();
    }
}
