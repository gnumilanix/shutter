package com.milanix.shutter.user.profile.followings;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.notification.NotificationGenerator;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing following list related dependencies
 *
 * @author milan
 */
@Module
public class FollowingListModule {
    private final FollowingListContract.View view;

    public FollowingListModule(FollowingListContract.View view) {
        this.view = view;
    }

    @Provides
    public FollowingListContract.View provideView() {
        return view;
    }

    @Provides
    public FollowingListContract.Presenter providePresenter(FollowingListContract.View view,
                                                            FirebaseUser user,
                                                            FirebaseDatabase database,
                                                            NotificationGenerator notificationGenerator,
                                                            @Named(ProfileModule.PROFILE_ID) String profileId) {
        return new FollowingListPresenter(view, user, database, notificationGenerator, profileId);
    }
}
