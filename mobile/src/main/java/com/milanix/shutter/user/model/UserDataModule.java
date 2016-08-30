package com.milanix.shutter.user.model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module providing user data related dependencies
 *
 * @author milan
 */
@Module
public class UserDataModule {

    @Provides
    @Singleton
    public UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

}
