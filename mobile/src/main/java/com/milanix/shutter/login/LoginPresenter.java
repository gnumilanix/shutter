package com.milanix.shutter.login;

import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.core.specification.IStore;
import com.milanix.shutter.notification.model.NotificationMessagingService;
import com.milanix.shutter.user.account.IAccountStore;
import com.milanix.shutter.user.auth.Authorization;
import com.milanix.shutter.user.auth.IAuthStore;
import com.milanix.shutter.user.model.IUserRepository;
import com.milanix.shutter.user.model.User;

import javax.inject.Inject;

/**
 * Login presenter
 *
 * @author milan
 */
public class LoginPresenter extends AbstractPresenter<LoginContract.View> implements LoginContract.Presenter {
    private final App app;
    private final IAuthStore authStore;
    private final IAccountStore accountStore;
    private final IUserRepository userRepository;
    private final MessageSubscriber messageSubscriber;

    @Inject
    public LoginPresenter(LoginContract.View view, App app, IAuthStore authStore, IAccountStore accountStore, IUserRepository userRepository, MessageSubscriber messageSubscriber) {
        super(view);
        this.app = app;
        this.authStore = authStore;
        this.accountStore = accountStore;
        this.userRepository = userRepository;
        this.messageSubscriber = messageSubscriber;
    }

    @Override
    public void subscribe() {
        view.showProgress();

        accountStore.getAuthToken(accountStore.getDefaultAccount(), new IStore.Callback<String>() {
            @Override
            public void onSuccess(String result) {
                subscribeNotifications();
                getSelf();
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
            }
        });
    }

    @Override
    public void login(final Login login) {
        view.showProgress();
        authStore.signIn(login.getUsername(), login.getPassword(), new IStore.Callback<Authorization>() {
            @Override
            public void onSuccess(Authorization result) {
                subscribeNotifications();
                getSelf();
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                view.handleInvalidLogin();
            }
        });
    }

    private void subscribeNotifications() {
        messageSubscriber.subscribe(NotificationMessagingService.NOTIFICATIONS);
    }

    private void getSelf() {
        userRepository.getSelf(new IStore.Callback<User>() {
            @Override
            public void onSuccess(User result) {
                app.createUserComponent(result);
                view.setSessionAvailable();
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgress();
                view.handleInvalidLogin();
            }
        });
    }
}
