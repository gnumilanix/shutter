package com.milanix.shutter.home;

import com.milanix.shutter.user.model.IUserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing home related dependencies
 *
 * @author milan
 */
@Module
public class HomeModule {
    private final HomeContract.View view;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @Provides
    public HomeContract.View provideHomeView() {
        return view;
    }

    @Provides
    public HomeContract.Presenter provideHomePresenter(HomeContract.View view,  IUserRepository userRepository) {
        return new HomePresenter(view,  userRepository);
    }
}
