package com.milanix.shutter.feed;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.feed.detail.PostDetailComponent;
import com.milanix.shutter.feed.detail.PostDetailModule;

import dagger.Subcomponent;

/**
 * Component that binds feeds related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = PostModule.class)
public interface PostComponent {
    PostDetailComponent with(PostDetailModule module);
}
