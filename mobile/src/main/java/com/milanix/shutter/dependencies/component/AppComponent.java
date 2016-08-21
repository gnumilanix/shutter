package com.milanix.shutter.dependencies.component;

import com.milanix.shutter.App;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.dependencies.module.DataModule;
import com.milanix.shutter.dependencies.module.NetworkModule;
import com.milanix.shutter.dependencies.module.SystemModule;
import com.milanix.shutter.login.LoginComponent;
import com.milanix.shutter.login.LoginModule;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;
import com.milanix.shutter.user.auth.Authenticator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component that binds application related component and provide dependencies to it
 *
 * @author milan
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, NetworkModule.class, SystemModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(Authenticator authenticator);

    LoginComponent with(LoginModule module);

    UserComponent with(UserModule module);

}
