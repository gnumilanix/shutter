package com.milanix.shutter.user.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.milanix.shutter.core.BundleBuilder;
import com.milanix.shutter.core.IStore;

import java.io.IOException;

import javax.inject.Inject;

import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static com.milanix.shutter.user.account.AccountAuthenticator.ACCOUNT_PASSWORD;
import static com.milanix.shutter.user.account.AccountAuthenticator.ACCOUNT_REFRESH_TOKEN;
import static com.milanix.shutter.user.account.AccountAuthenticator.ACCOUNT_TYPE;
import static com.milanix.shutter.user.account.AccountAuthenticator.TOKEN_BEARER;

/**
 * Class that provides account related information
 *
 * @author milan
 */
public class AccountStore implements IAccountStore {
    private final AccountManager accountManager;

    @Inject
    public AccountStore(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public Account getDefaultAccount() {
        final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);

        if (accounts.length > 0) {
            return accounts[0];
        }

        return null;
    }

    /**
     * Returns if the given account exist
     *
     * @param account to test
     * @return true if account exist
     */
    @Override
    public boolean hasAccount(Account account) {
        final Account[] accounts = accountManager.getAccountsByType(account.type);

        for (Account test : accounts) {
            if (test.name.equals(account.name))
                return true;
        }

        return false;
    }

    @Override
    @Nullable
    public String getCachedAuthToken(Account account) {
        return accountManager.peekAuthToken(account, TOKEN_BEARER);
    }

    @Override
    public String getAuthToken(Account account) throws Exception {
        return accountManager.blockingGetAuthToken(account, ACCOUNT_TYPE, false);
    }

    @Override
    public void getAuthToken(Account account, final IStore.Callback<String> callback) {
        if (null != account) {
            accountManager.getAuthToken(account, TOKEN_BEARER, null, false, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                    try {
                        final String token = accountManagerFuture.getResult().getString(AccountManager.KEY_AUTHTOKEN);

                        if (!TextUtils.isEmpty(token))
                            callback.onSuccess(token);
                        else
                            throw new NullPointerException("Token not found");
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                }
            }, null);
        } else {
            callback.onFailure(new NullPointerException("Account not found"));
        }
    }

    @Override
    public void invalidateAuthToken(Account account, String token) {
        accountManager.invalidateAuthToken(ACCOUNT_TYPE, token);
    }

    @Override
    public void putAccount(@NonNull String username, @NonNull String password, @NonNull String authToken,
                           @NonNull String refreshToken) {
        final Account account = new Account(username, ACCOUNT_TYPE);

        if (hasAccount(account))
            accountManager.setPassword(account, password);
        else {
            accountManager.addAccountExplicitly(account, password, new BundleBuilder()
                    .putString(KEY_ACCOUNT_TYPE, ACCOUNT_TYPE)
                    .putString(KEY_ACCOUNT_NAME, username)
                    .putString(KEY_AUTHTOKEN, authToken)
                    .putString(ACCOUNT_PASSWORD, password)
                    .putString(ACCOUNT_REFRESH_TOKEN, refreshToken).build());
        }

        accountManager.setAuthToken(account, ACCOUNT_TYPE, authToken);
    }

    @Override
    @WorkerThread
    public boolean deleteAccount(Account account) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return accountManager.removeAccountExplicitly(account);
        } else {
            try {
                return accountManager.removeAccount(account, null, null).getResult();
            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                return false;
            }
        }
    }

    @Override
    public void deleteAccount(Account account, final IStore.Callback<Void> callback) {
        removeAccount(account, new IStore.Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                if (null != callback)
                    callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable t) {
                if (null != callback)
                    callback.onFailure(t);
            }
        });

    }

    /**
     * Removes given account
     *
     * @param account to remove
     */
    private void removeAccount(final Account account, final IStore.Callback<Void> callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (accountManager.removeAccountExplicitly(account))
                callback.onSuccess(null);
            else
                callback.onFailure(new Exception("Cannot remove the account"));
        } else {
            accountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {
                @Override
                public void run(AccountManagerFuture<Boolean> accountManagerFuture) {
                    try {
                        if (accountManagerFuture.getResult())
                            callback.onSuccess(null);
                        else
                            throw new Exception("Cannot remove the account");
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                }
            }, null);
        }
    }
}