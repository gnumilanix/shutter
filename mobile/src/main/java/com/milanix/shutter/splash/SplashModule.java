package com.milanix.shutter.splash;

import com.google.firebase.auth.FirebaseAuth;
import com.milanix.shutter.App;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class SplashModule {
    private final SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @Provides
    public SplashContract.View provideSplashView() {
        return view;
    }

    @Provides
    public SplashContract.Presenter provideSplashPresenter(SplashContract.View view, App app,
                                                           FirebaseAuth auth) {
        return new SplashPresenter(view, app, auth);
    }
}
