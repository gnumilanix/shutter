package com.milanix.shutter.auth;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {LandingModule.class})
public interface LandingComponent {
    void inject(LandingFragment fragment);
}
