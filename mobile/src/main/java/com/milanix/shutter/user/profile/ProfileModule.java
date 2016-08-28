package com.milanix.shutter.user.profile;

import com.milanix.shutter.App;
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
                                                             IUserRepository userRepository,
                                                             IFeedRepository feedRepository,
                                                             IAuthStore authStore, App app) {
        return new ProfilePresenter(view, userRepository, feedRepository, authStore, app);
    }
}
