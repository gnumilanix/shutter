package com.milanix.shutter.user.auth;

import javax.inject.Inject;

import static com.milanix.shutter.BuildConfig.CLIENT_ID;
import static com.milanix.shutter.BuildConfig.CLIENT_SECRET;
import static com.milanix.shutter.user.auth.Authenticator.GRANT_PASSWORD;
import static com.milanix.shutter.user.auth.Authenticator.GRANT_REFRESH_TOKEN;

/**
 * Store that provide authentication from remote data source
 *
 * @author milan
 */
public class OAuthStore implements IAuthStore {
    private AuthApi api;

    @Inject
    public OAuthStore(AuthApi api) {
        this.api = api;
    }

    @Override
    public Authorization signUp(String username, String password, String email, String firstName,
                                String lastName) throws Exception {
        return api.signUp(username, password, email, firstName, lastName).execute().body();
    }

    @Override
    public Authorization signIn(String username, String password) throws Exception {
        return api.authorize(username, password, GRANT_PASSWORD, CLIENT_ID, CLIENT_SECRET).execute().body();
    }

    @Override
    public Authorization refreshToken(String refreshToken) throws Exception {
        return api.refreshToken(GRANT_REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET, refreshToken).execute().body();
    }

    @Override
    public void logout(String accessToken) throws Exception {
        api.logout(accessToken).execute().body();
    }
}
