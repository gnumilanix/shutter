package com.milanix.shutter.user.auth;

import android.accounts.Account;
import android.text.TextUtils;

import com.milanix.shutter.user.account.IAccountStore;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

/**
 * OAuth interceptor to be used with retrofit that adds auth headers too all requests going through
 * this interceptor
 *
 * @author milan
 */
public class AuthorizationInterceptor implements Interceptor {
    public static final String BEARER_PREFIX = "Bearer ";
    private IAccountStore accountStore;

    @Inject
    public AuthorizationInterceptor(IAccountStore accountStore) {
        this.accountStore = accountStore;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        final Account account = accountStore.getDefaultAccount();

        if (null != account) {
            final String accessToken = accountStore.getCachedAuthToken(account);

            if (!TextUtils.isEmpty(accessToken))
                return chain.proceed(request.newBuilder()
                        .header(AUTHORIZATION, BEARER_PREFIX + accessToken)
                        .build());
        }

        return chain.proceed(request);
    }

}
