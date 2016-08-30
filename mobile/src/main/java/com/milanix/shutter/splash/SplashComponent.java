package com.milanix.shutter.splash;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.login.LoginModule;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = {SplashModule.class})
public interface SplashComponent {
    void inject(SplashActivity activity);
}
