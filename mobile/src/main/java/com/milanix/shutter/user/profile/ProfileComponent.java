package com.milanix.shutter.user.profile;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds profile detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = ProfileModule.class)
public interface ProfileComponent {
    void inject(ProfileFragment fragment);
}
