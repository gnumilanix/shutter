package com.milanix.shutter.user;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.dependencies.scope.UserScope;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing user related dependencies
 *
 * @author milan
 */
@Module
public class UserModule {
    private final FirebaseUser user;

    public UserModule(FirebaseUser user) {
        this.user = user;
    }

    @Provides
    @UserScope
    public FirebaseUser provideUser() {
        return user;
    }
}
