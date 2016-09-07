package com.milanix.shutter.user;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.dependencies.scope.UserScope;
import com.milanix.shutter.notification.Notifier;

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

    @UserScope
    @Provides
    public Notifier provideNotificationGenerator(FirebaseUser user, FirebaseDatabase database) {
        return new Notifier(user, database);
    }
}
