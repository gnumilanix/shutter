package com.milanix.shutter.user.profile.followers;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.notification.NotificationGenerator;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing follower list related dependencies
 *
 * @author milan
 */
@Module
public class FollowerListModule {
    private final FollowerListContract.View view;

    public FollowerListModule(FollowerListContract.View view) {
        this.view = view;
    }

    @Provides
    public FollowerListContract.View provideView() {
        return view;
    }

    @Provides
    public FollowerListContract.Presenter providePresenter(FollowerListContract.View view,
                                                           FirebaseUser user,
                                                           FirebaseDatabase database,
                                                           NotificationGenerator notificationGenerator,
                                                           @Named(ProfileModule.PROFILE_ID) String profileId) {
        return new FollowerListPresenter(view, user, database, notificationGenerator, profileId);
    }
}
