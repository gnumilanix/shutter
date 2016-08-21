package com.milanix.shutter.user;

import com.milanix.shutter.dependencies.scope.UserScope;
import com.milanix.shutter.user.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing user related dependencies
 *
 * @author milan
 */
@Module
public class UserModule {
    private final User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    public User provideUser() {
        return user;
    }
}
