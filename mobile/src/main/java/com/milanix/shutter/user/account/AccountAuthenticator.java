package com.milanix.shutter.user.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.milanix.shutter.App;
import com.milanix.shutter.auth.login.LoginFragment;
import com.milanix.shutter.core.BundleBuilder;

import javax.inject.Inject;

import timber.log.Timber;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.KEY_INTENT;

/**
 * AccountAuthenticator that authenticates this user account
 *
 * @author milan
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {
    private final static String ARG_ACCOUNT_TYPE = "_account_type";
    private final static String ARG_AUTH_TYPE = "_auth_type";
    private final static String ARG_IS_ADDING_NEW_ACCOUNT = "_is_adding_account";
    private final static String ARG_ACCOUNT_FEATURES = "_account_features";

    public static final String ACCOUNT_TYPE = "shutter.com";
    public static final String ACCOUNT_PASSWORD = "_password";
    public static final String ACCOUNT_REFRESH_TOKEN = "_refresh_token";
    public static final String TOKEN_BEARER = "Bearer";
    public static final String TOKEN_BEARER_FULL_LABEL = "Full user access to example account";


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
        Timber.d("adding new account");
        return new BundleBuilder().putParcelable(KEY_INTENT, new Intent(context, LoginFragment.class).
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
        throw new UnsupportedOperationException("getAuthToken not supported by this authenticator");
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
        Timber.d("updating credentials");

        return new BundleBuilder().putParcelable(KEY_INTENT, new Intent(context, LoginFragment.class).
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

}
