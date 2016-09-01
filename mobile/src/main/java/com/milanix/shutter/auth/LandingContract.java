package com.milanix.shutter.auth;

import android.content.Intent;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

import java.util.List;

/**
 * Contract for auth related implementations
 *
 * @author milan
 */
public interface LandingContract {
    interface View extends IView {
        void login();

        void signUp();

        void completeLogin();

        void handleLoginError();

        void openAgreement();

        void showProgress();

        void hideProgress();

        void loginWithGoogle(Intent intent, int requestCode);

        void handleGoogleApiFailure();

        void loginWithFacebook(List<String> permissions);
    }

    interface Presenter extends IPresenter {
        void loginWithGoogle();

        void loginWithFacebook();

        void handleActivityResult(int requestCode, int resultCode, Intent data);
    }
}
