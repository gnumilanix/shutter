package com.milanix.shutter.login;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {AuthModule.class})
public interface AuthComponent {
    void inject(AuthFragment fragment);
}
