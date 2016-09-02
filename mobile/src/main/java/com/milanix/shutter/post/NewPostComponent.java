package com.milanix.shutter.post;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds new post detail related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = NewPostModule.class)
public interface NewPostComponent {
    void inject(NewPostFragment fragment);
}
