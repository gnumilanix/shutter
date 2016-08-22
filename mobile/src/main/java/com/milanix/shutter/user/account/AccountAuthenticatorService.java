package com.milanix.shutter.user.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

/**
 * A bound Service that instantiates the accountAuthenticator when started.
 *
 * @author milan
 */
public class AccountAuthenticatorService extends Service {
    private AccountAuthenticator accountAuthenticator;

    @Override
    public void onCreate() {
        accountAuthenticator = new AccountAuthenticator(this);
    }

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return accountAuthenticator.getIBinder();
    }
}
