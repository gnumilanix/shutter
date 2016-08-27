package com.milanix.shutter.feed.detail;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds feed detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FeedDetailModule.class)
public interface FeedDetailComponent {
    void inject(FeedDetailFragment fragment);
}
