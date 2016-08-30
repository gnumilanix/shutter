package com.milanix.shutter.login;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class AuthModule {
    private final AuthContract.View view;

    public AuthModule(AuthContract.View view) {
        this.view = view;
    }

    @Provides
    public AuthContract.View provideAuthView() {
        return view;
    }

    @Provides
    public AuthContract.Presenter provideAuthPresenter(AuthContract.View view) {
        return new AuthPresenter(view);
    }
}
