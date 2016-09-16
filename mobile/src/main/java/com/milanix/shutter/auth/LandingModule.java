package com.milanix.shutter.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.milanix.shutter.App;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class LandingModule {
    private final LandingContract.View view;

    public LandingModule(LandingContract.View view) {
        this.view = view;
    }

    @Provides
    public LandingContract.View provideView() {
        return view;
    }

    @Provides
    public LandingContract.Presenter providePresenter(LandingContract.View view, App app,
                                                      FirebaseAuth auth, FirebaseDatabase database,
                                                      FirebaseRemoteConfig remoteConfig,
                                                      GoogleSignInOptions googleSignInOptions) {
        return new LandingPresenter(view, app, auth, database, remoteConfig, googleSignInOptions);
    }
}
