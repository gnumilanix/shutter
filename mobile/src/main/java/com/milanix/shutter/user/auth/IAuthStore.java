package com.milanix.shutter.user.auth;

import com.milanix.shutter.core.IStore;

/**
 * Interface to be implemented by authentication stores
 *
 * @author milan
 */
public interface IAuthStore extends IStore {
    void initExistingSession(Callback<Authorization> callback);

    void signUp(String username, String password, String email, String firstName, String lastName,
                Callback<Authorization> callback);

    void signIn(String username, String password, Callback<Authorization> callback);

    Authorization signIn(String username, String password) throws Exception;

    Authorization refreshToken(String refreshToken) throws Exception;

    void logout(Callback<Void> callback);

}
