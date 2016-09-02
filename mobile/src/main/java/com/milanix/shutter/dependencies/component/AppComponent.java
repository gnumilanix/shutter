package com.milanix.shutter.dependencies.component;

import com.milanix.shutter.App;
import com.milanix.shutter.auth.LandingComponent;
import com.milanix.shutter.auth.LandingModule;
import com.milanix.shutter.auth.login.LoginComponent;
import com.milanix.shutter.auth.login.LoginModule;
import com.milanix.shutter.auth.resetpassword.RequestPasswordComponent;
import com.milanix.shutter.auth.resetpassword.RequestPasswordModule;
import com.milanix.shutter.auth.signup.SignUpComponent;
import com.milanix.shutter.auth.signup.SignUpModule;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.dependencies.module.AuthModule;
import com.milanix.shutter.dependencies.module.DataModule;
import com.milanix.shutter.dependencies.module.FirebaseModule;
import com.milanix.shutter.dependencies.module.LogModule;
import com.milanix.shutter.dependencies.module.SystemModule;
import com.milanix.shutter.splash.SplashComponent;
import com.milanix.shutter.splash.SplashModule;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;
import com.milanix.shutter.user.account.AccountAuthenticator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component that binds application related component and provide dependencies to it
 *
 * @author milan
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class, SystemModule.class, LogModule.class, AuthModule.class,
        FirebaseModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(AccountAuthenticator accountAuthenticator);

    SplashComponent with(SplashModule module);

    LandingComponent with(LandingModule landingModule);

    LoginComponent with(LoginModule module);

    SignUpComponent with(SignUpModule module);

    RequestPasswordComponent with(RequestPasswordModule module);

    UserComponent with(UserModule module);
}
