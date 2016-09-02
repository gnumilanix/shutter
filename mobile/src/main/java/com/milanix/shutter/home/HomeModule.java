package com.milanix.shutter.home;

import com.google.firebase.auth.FirebaseUser;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing home related dependencies
 *
 * @author milan
 */
@Module
public class HomeModule {
    private final HomeContract.View view;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @Provides
    public HomeContract.View provideView() {
        return view;
    }

    @Provides
    public HomeContract.Presenter providePresenter(HomeContract.View view, FirebaseUser user) {
        return new HomePresenter(view, user);
    }
}
