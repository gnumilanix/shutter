package com.milanix.shutter.user.profile.followers;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds profile detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FollowerListModule.class)
public interface FollowerListComponent {
    void inject(FollowerListFragment fragment);
}
