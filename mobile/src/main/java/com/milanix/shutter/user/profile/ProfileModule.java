package com.milanix.shutter.user.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.App;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.feed.model.IFeedRepository;
import com.milanix.shutter.user.auth.IAuthStore;
import com.milanix.shutter.user.model.IUserRepository;

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
    public ProfileContract.View provideProfileView() {
        return view;
    }

    @Provides
    public ProfileContract.Presenter provideProfilePresenter(ProfileContract.View view,
                                                             App app, MessageSubscriber subscriber,
                                                             FirebaseUser user,
                                                             FirebaseAuth auth,
                                                             FirebaseDatabase database ) {
        return new ProfilePresenter(view,app, subscriber,user,auth,database);
    }
}
