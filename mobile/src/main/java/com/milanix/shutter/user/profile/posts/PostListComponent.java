package com.milanix.shutter.user.profile.posts;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds profile detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = PostListModule.class)
public interface PostListComponent {
    void inject(PostListFragment fragment);
}
