package com.milanix.shutter.user.profile.followings;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds profile detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FollowingListModule.class)
public interface FollowingListComponent {
    void inject(FollowingListFragment fragment);
}
