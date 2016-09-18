package com.milanix.shutter.web;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds web page component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = WebPageModule.class)
public interface WebPageComponent {
    void inject(WebPageFragment fragment);
}
