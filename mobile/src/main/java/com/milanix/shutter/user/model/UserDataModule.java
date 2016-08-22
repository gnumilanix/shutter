package com.milanix.shutter.user.model;

import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.qualifier.Remote;
import com.milanix.shutter.user.account.IAccountStore;

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

    @Provides
    @Local
    @Singleton
    public IUserStore provideLocalUserStore(IAccountStore accountStore) {
        return new LocalUserStore(accountStore);
    }

    @Provides
    @Remote
    @Singleton
    public IUserStore provideRemoteUserStore(UserApi UserApi) {
        return new RemoteUserStore(UserApi);
    }

    @Provides
    @Singleton
    public IUserRepository provideUserRepository(@Local IUserStore local, @Remote IUserStore remote) {
        return new UserRepository(local, remote);
    }

}
