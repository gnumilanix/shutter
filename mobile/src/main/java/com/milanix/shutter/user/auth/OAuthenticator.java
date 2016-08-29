package com.milanix.shutter.user.auth;

import android.accounts.Account;
import android.text.TextUtils;

import com.milanix.shutter.user.account.IAccountStore;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import timber.log.Timber;

/**
 * Authenticator that will be used by ok http
 *
 * @author milan
 */
public class OAuthenticator implements Authenticator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    private IAccountStore accountProvider;

    @Inject
    public OAuthenticator(IAccountStore accountProvider) {
        this.accountProvider = accountProvider;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        try {
            final Account account = accountProvider.getDefaultAccount();
            final String accessToken = accountProvider.getCachedAuthToken(account);

            if (TextUtils.isEmpty(accessToken)) {
                final String refreshedToken = accountProvider.getAuthToken(account);

                if (TextUtils.isEmpty(refreshedToken)) {
                    accountProvider.deleteAccount(account);
                } else {
                    return response.request().newBuilder().addHeader(HEADER_AUTHORIZATION, refreshedToken).build();
                }
            } else {
                accountProvider.invalidateAuthToken(account, accessToken);
            }
        } catch (Exception e) {
            Timber.i(e, "Failed to authenticate");
        }

        return response.request();
    }

}
