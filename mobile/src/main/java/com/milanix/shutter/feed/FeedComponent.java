package com.milanix.shutter.feed;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.feed.detail.FeedDetailComponent;
import com.milanix.shutter.feed.detail.FeedDetailModule;

import dagger.Subcomponent;

/**
 * Component that binds feeds related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = FeedModule.class)
public interface FeedComponent {
    FeedDetailComponent with(FeedDetailModule module);
}
