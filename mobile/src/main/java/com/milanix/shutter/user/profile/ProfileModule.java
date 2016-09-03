package com.milanix.shutter.user.profile;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing profile related dependencies
 *
 * @author milan
 */
@Module
public class ProfileModule {
    public static final String PROFILE_ID = "_profile_id";

    private final String profileId;

    public ProfileModule(String profileId) {
        this.profileId = profileId;
    }

    @Provides
    @Named(PROFILE_ID)
    public String provideId() {
        return profileId;
    }
}
