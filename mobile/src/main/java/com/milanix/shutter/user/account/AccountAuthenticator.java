package com.milanix.shutter.user.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.milanix.shutter.App;
import com.milanix.shutter.core.BundleBuilder;
import com.milanix.shutter.login.view.LoginActivity;
import com.milanix.shutter.user.auth.IAuthStore;

import javax.inject.Inject;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.KEY_ERROR_CODE;
import static android.accounts.AccountManager.KEY_INTENT;

/**
 * AccountAuthenticator that authenticates this user account
 *
 * @author milan
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {
    public final static String ARG_ACCOUNT_TYPE = "_account_type";
    public final static String ARG_ACCOUNT_NAME = "_account_name";
    public final static String ARG_AUTH_TYPE = "_auth_type";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "_is_adding_account";
    public final static String ARG_ACCOUNT_FEATURES = "_account_features";

    public static final String ACCOUNT_TYPE = "shutter.com";
    public static final String ACCOUNT_PASSWORD = "_password";
    public static final String ACCOUNT_REFRESH_TOKEN = "_refresh_token";
    public static final String TOKEN_BEARER = "Bearer";
    public static final String TOKEN_BEARER_FULL_LABEL = "Full user access to example account";

    public static final String GRANT_PASSWORD = "password";
    public static final String GRANT_REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_AUTHORIZATION_CODE = "authorization_code";

    public static final String ERROR_INVALID_TOKEN_TYPE = "INVALID_TOKEN_TYPE";

    @Inject
    protected IAuthStore authStore;
    @Inject
    protected AccountManager manager;

    protected final Context context;

    public AccountAuthenticator(Context context) {
        super(context);
        this.context = context;

        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        throw new UnsupportedOperationException("Edit properties not supported by this authenticator");
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return new BundleBuilder().putParcelable(KEY_INTENT, new Intent(context, LoginActivity.class).
                putExtra(ARG_ACCOUNT_TYPE, accountType).
                putExtra(ARG_AUTH_TYPE, authTokenType).
                putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true).
                putExtra(ARG_ACCOUNT_FEATURES, requiredFeatures).
                putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response).
                putExtras(options)
        ).build();
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
                                     Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException("Confirm credential not supported by this authenticator");
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType,
                               Bundle options) throws NetworkErrorException {
        if (authTokenType.equals(TOKEN_BEARER)) {
            final String authToken = getToken(account, authTokenType);

            if (TextUtils.isEmpty(authToken)) {
                return new BundleBuilder().putParcelable(KEY_INTENT, new Intent(context, LoginActivity.class).
                        putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response).
                        putExtra(ARG_ACCOUNT_TYPE, account.type).
                        putExtra(ARG_AUTH_TYPE, authTokenType).
                        putExtra(ARG_ACCOUNT_NAME, account.name))
                        .build();
            } else {
                manager.setAuthToken(account, authTokenType, authToken);

                return new BundleBuilder().putString(KEY_ACCOUNT_NAME, account.name)
                        .putString(KEY_ACCOUNT_TYPE, account.type)
                        .putString(KEY_AUTHTOKEN, authToken).build();
            }
        }

        return new BundleBuilder().putString(KEY_ERROR_CODE, ERROR_INVALID_TOKEN_TYPE).build();
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (TOKEN_BEARER.equals(authTokenType))
            return TOKEN_BEARER_FULL_LABEL;
        else
            return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
                                    String authTokenType, Bundle options) throws NetworkErrorException {
        return new BundleBuilder().putParcelable(KEY_INTENT, new Intent(context, LoginActivity.class).
                putExtra(ARG_ACCOUNT_TYPE, account.type).
                putExtra(ARG_AUTH_TYPE, account).
                putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true).
                putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response).
                putExtras(options))
                .build();
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
                              String[] features) throws NetworkErrorException {
        return new BundleBuilder().putBoolean(KEY_BOOLEAN_RESULT, false).build();
    }

    /**
     * Returns a auth token, if does not exist will try to refresh with the given account
     *
     * @param account       to get token for
     * @param authTokenType to get
     * @return token if available, otherwise null
     */
    private String getToken(Account account, String authTokenType) {
        final String authToken = manager.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            try {
                return authStore.signIn(account.name, manager.getPassword(account)).getAccessToken();
            } catch (Exception ignored) {
                return null;
            }
        } else
            return authToken;
    }
}
