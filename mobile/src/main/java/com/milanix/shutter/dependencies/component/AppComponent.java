package com.milanix.shutter.dependencies.component;

import com.milanix.shutter.App;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.dependencies.module.AuthenticationModule;
import com.milanix.shutter.dependencies.module.DataModule;
import com.milanix.shutter.dependencies.module.LogModule;
import com.milanix.shutter.dependencies.module.NetworkModule;
import com.milanix.shutter.dependencies.module.SystemModule;
import com.milanix.shutter.login.AuthComponent;
import com.milanix.shutter.login.AuthModule;
import com.milanix.shutter.login.LoginComponent;
import com.milanix.shutter.login.LoginModule;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;
import com.milanix.shutter.user.account.AccountAuthenticator;
import com.milanix.shutter.user.model.UserDataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component that binds application related component and provide dependencies to it
 *
 * @author milan
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, NetworkModule.class, SystemModule.class, LogModule.class, AuthenticationModule.class, UserDataModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(AccountAuthenticator accountAuthenticator);

    AuthComponent with(AuthModule authModule);

    LoginComponent with(LoginModule module);

    UserComponent with(UserModule module);

}
