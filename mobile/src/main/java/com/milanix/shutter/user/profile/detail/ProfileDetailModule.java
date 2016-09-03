package com.milanix.shutter.user.profile.detail;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.App;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing profile related dependencies
 *
 * @author milan
 */
@Module
public class ProfileDetailModule {
    private final ProfileDetailContract.View view;

    public ProfileDetailModule(ProfileDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public ProfileDetailContract.View provideView() {
        return view;
    }

    @Provides
    public ProfileDetailContract.Presenter providePresenter(ProfileDetailContract.View view,
                                                            App app,
                                                            FirebaseUser user,
                                                            FirebaseAuth auth,
                                                            FirebaseDatabase database,
                                                            GoogleSignInOptions googleSignInOptions,
                                                            @Named(ProfileModule.PROFILE_ID) String profileId) {
        return new ProfileDetailPresenter(view, app, user, auth, database, googleSignInOptions, profileId);
    }
}
