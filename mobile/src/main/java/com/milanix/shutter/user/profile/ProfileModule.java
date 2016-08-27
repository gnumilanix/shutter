package com.milanix.shutter.user.profile;

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
                                                             IUserRepository feedRepository) {
        return new ProfilePresenter(view, feedRepository);
    }
}
