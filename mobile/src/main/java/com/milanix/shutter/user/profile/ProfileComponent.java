package com.milanix.shutter.user.profile;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.user.profile.detail.ProfileDetailComponent;
import com.milanix.shutter.user.profile.detail.ProfileDetailModule;

import dagger.Subcomponent;

/**
 * Component that binds profile related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = ProfileModule.class)
public interface ProfileComponent {
    ProfileDetailComponent with(ProfileDetailModule module);
}
