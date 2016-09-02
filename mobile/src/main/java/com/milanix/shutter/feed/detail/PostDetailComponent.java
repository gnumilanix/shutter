package com.milanix.shutter.feed.detail;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds feed detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = PostDetailModule.class)
public interface PostDetailComponent {
    void inject(PostDetailFragment fragment);
}
