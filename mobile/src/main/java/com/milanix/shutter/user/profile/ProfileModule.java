package com.milanix.shutter.user.profile;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.App;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing profile related dependencies
 *
 * @author milan
 */
@Module
public class ProfileModule {
    private final ProfileContract.View view;

    public ProfileModule(ProfileContract.View view) {
        this.view = view;
    }

    @Provides
    public ProfileContract.View provideView() {
        return view;
    }

    @Provides
    public ProfileContract.Presenter providePresenter(ProfileContract.View view,
                                                      App app,
                                                      FirebaseUser user,
                                                      FirebaseAuth auth,
                                                      FirebaseDatabase database,
                                                      GoogleSignInOptions googleSignInOptions) {
        return new ProfilePresenter(view, app, user, auth, database, googleSignInOptions);
    }
}
