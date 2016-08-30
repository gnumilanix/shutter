package com.milanix.shutter.login;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {SignUpModule.class})
public interface SignUpComponent {
    void inject(SignUpFragment fragment);
}
