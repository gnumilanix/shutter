package com.milanix.shutter.splash;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Login presenter
 *
 * @author milan
 */
public class SplashPresenter extends AbstractPresenter<SplashContract.View> implements SplashContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;

    @Inject
    public SplashPresenter(SplashContract.View view, App app, FirebaseAuth auth) {
        super(view);
        this.app = app;
        this.auth = auth;
    }

    @Override
    public void subscribe() {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    app.createUserComponent(user);

                    if (isActive())
                        view.setSessionAvailable();
                } else {
                    if (isActive())
                        view.setSessionUnavailable();
                }
            }
        });
    }

}
