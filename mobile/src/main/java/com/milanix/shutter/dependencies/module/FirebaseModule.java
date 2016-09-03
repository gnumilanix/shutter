package com.milanix.shutter.dependencies.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing auth related dependencies
 *
 * @author milan
 */
@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseAuth provideAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    public FirebaseDatabase provideDatabase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        return database;
    }

    @Singleton
    @Provides
    public FirebaseStorage provideStorageReference() {
        return FirebaseStorage.getInstance();
    }
}
