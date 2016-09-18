package com.milanix.shutter.auth.resetpassword;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Reset password presenter
 *
 * @author milan
 */
public class RequestPasswordPresenter extends AbstractPresenter<RequestPasswordContract.View> implements RequestPasswordContract.Presenter {
    private final FirebaseAuth auth;

    @Inject
    public RequestPasswordPresenter(RequestPasswordContract.View view, FirebaseAuth auth) {
        super(view);
        this.auth = auth;
    }

    @Override
    public void requestPassword(final RequestPassword requestPassword) {
        view.showProgress();
        auth.sendPasswordResetEmail(requestPassword.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (isActive()) {
                            if (task.isSuccessful()) {
                                view.completeRequestPassword(requestPassword.getEmail());
                            } else {
                                view.handleResetPasswordFailure();
                            }

                            view.hideProgress();
                        }
                    }
                });
    }
}
