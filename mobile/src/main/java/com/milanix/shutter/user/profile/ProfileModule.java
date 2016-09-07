package com.milanix.shutter.user.profile;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.App;
import com.milanix.shutter.notification.Notifier;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing profile related dependencies
 *
 * @author milan
 */
@Module
public class ProfileModule {
    public static final String PROFILE_ID = "_profile_id";

    private final String profileId;
    private final ProfileContract.View view;

    public ProfileModule(String profileId, ProfileContract.View view) {
        this.profileId = profileId;
        this.view = view;
    }

    @Provides
    @Named(PROFILE_ID)
    public String provideId() {
        return profileId;
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
                                                      GoogleSignInOptions googleSignInOptions,
                                                      Notifier notifier,
                                                      @Named(ProfileModule.PROFILE_ID) String profileId) {
        return new ProfilePresenter(view, app, user, auth, database, googleSignInOptions, notifier, profileId);
    }
}
