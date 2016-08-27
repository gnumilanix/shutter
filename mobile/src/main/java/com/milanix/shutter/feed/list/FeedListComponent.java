package com.milanix.shutter.feed.list;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds feeds related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FeedListModule.class)
public interface FeedListComponent {
    void inject(FeedListFragment fragment);
}
