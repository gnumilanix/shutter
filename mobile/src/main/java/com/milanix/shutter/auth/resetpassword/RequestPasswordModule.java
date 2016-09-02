package com.milanix.shutter.auth.resetpassword;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class RequestPasswordModule {
    private final RequestPasswordContract.View view;

    public RequestPasswordModule(RequestPasswordContract.View view) {
        this.view = view;
    }

    @Provides
    public RequestPasswordContract.View provideView() {
        return view;
    }

    @Provides
    public RequestPasswordContract.Presenter providePresenter(RequestPasswordContract.View view,
                                                              FirebaseAuth auth) {
        return new RequestPasswordPresenter(view, auth);
    }
}
