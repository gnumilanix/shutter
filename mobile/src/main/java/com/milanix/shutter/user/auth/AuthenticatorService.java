package com.milanix.shutter.user.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

/**
 * A bound Service that instantiates the authenticator when started.
 *
 * @author milan
 */
public class AuthenticatorService extends Service {
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new Authenticator(getApplicationContext());
    }

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return authenticator.getIBinder();
    }
}
