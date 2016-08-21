package com.milanix.shutter.user.auth;

import com.milanix.shutter.core.IStore;

/**
 * Interface to be implemented by authentication stores
 *
 * @author milan
 */
public interface IAuthStore extends IStore {
    Authorization signUp(String username, String password, String email, String firstName, String lastName)
            throws Exception;

    Authorization signIn(String username, String password) throws Exception;

    Authorization refreshToken(String refreshToken) throws Exception;

    void logout(String accessToken) throws Exception;
}
