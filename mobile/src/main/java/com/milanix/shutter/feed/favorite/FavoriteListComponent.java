package com.milanix.shutter.feed.favorite;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds favorite feeds related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FavoriteListModule.class)
public interface FavoriteListComponent {
    void inject(FavoriteListFragment fragment);
}
