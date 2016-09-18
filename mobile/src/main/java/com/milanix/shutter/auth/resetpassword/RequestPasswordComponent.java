package com.milanix.shutter.auth.resetpassword;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds reset password related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {RequestPasswordModule.class})
public interface RequestPasswordComponent {
    void inject(RequestPasswordFragment fragment);
}
