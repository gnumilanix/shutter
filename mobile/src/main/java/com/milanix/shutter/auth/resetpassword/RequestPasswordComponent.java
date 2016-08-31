package com.milanix.shutter.auth.resetpassword;

import com.milanix.shutter.auth.signup.SignUpFragment;
import com.milanix.shutter.auth.signup.SignUpModule;
import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {RequestPasswordModule.class})
public interface RequestPasswordComponent {
    void inject(RequestPasswordFragment fragment);
}
