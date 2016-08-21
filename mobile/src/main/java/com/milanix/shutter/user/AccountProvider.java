package com.milanix.shutter.user;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;

import static com.milanix.shutter.user.auth.Authenticator.ACCOUNT_TYPE;

/**
 * Class that provides account related information
 *
 * @author milan
 */
public class AccountProvider {
    private Context context;
    private AccountManager accountManager;

    @Inject
    public AccountProvider(Context context, AccountManager accountManager) {
        this.context = context;
        this.accountManager = accountManager;
    }

    public Account getAccount() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);

            if (accounts.length > 0) {
                return accounts[0];
            }
        }

        return null;
    }
}
