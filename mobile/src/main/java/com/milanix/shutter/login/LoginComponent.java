package com.milanix.shutter.login;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.login.view.LoginActivity;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
