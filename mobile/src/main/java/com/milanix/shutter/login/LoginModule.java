package com.milanix.shutter.login;

import com.milanix.shutter.App;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.user.account.IAccountStore;
import com.milanix.shutter.user.auth.IAuthStore;
import com.milanix.shutter.user.model.IUserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class LoginModule {
    private final LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    public LoginContract.View provideLoginView() {
        return view;
    }

    @Provides
    public LoginContract.Presenter provideLoginPresenter(LoginContract.View view, App app, IAuthStore authStore,
                                                         IAccountStore accountStore, IUserRepository userRepository,
                                                         MessageSubscriber messageSubscriber) {
        return new LoginPresenter(view, app, authStore, accountStore, userRepository,messageSubscriber);
    }
}
