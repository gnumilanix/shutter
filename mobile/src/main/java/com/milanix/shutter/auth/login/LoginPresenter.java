package com.milanix.shutter.auth.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * LoginModel presenter
 *
 * @author milan
 */
public class LoginPresenter extends AbstractPresenter<LoginContract.View> implements LoginContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;

    @Inject
    public LoginPresenter(LoginContract.View view, App app, FirebaseAuth auth) {
        super(view);
        this.app = app;
        this.auth = auth;
    }

    @Override
    public void login(final LoginModel login) {
        if (areLoginFieldsValid(login)) {
            if (isActive())
                view.showProgress();

            auth.signInWithEmailAndPassword(login.getUsername(), login.getPassword()).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult result) {
                            app.createUserComponent(result.getUser());

                            if (isActive())
                                view.setSessionAvailable();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (isActive()) {
                        view.hideProgress();
                        view.handleInvalidLogin();
                    }
                }
            });
        }
    }

    @Override
    public void requestPassword(String email) {

    }

    private boolean areLoginFieldsValid(LoginModel login) {
        if (TextUtils.isEmpty(login.getUsername())) {
            if (isActive())
                view.handleInvalidUsername();

            return false;
        } else if (TextUtils.isEmpty(login.getPassword())) {
            if (isActive())
                view.handleInvalidPassword();

            return false;
        }

        return true;
    }
}
