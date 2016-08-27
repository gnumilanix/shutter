package com.milanix.shutter.home;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds home related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity activity);
}
