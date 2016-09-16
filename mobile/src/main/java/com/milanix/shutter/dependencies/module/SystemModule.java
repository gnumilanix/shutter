package com.milanix.shutter.dependencies.module;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides system components
 *
 * @author milan
 */
@Module(includes = {AppModule.class})
public class SystemModule {

    @Provides
    @Singleton
    public LayoutInflater provideLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    @Singleton
    @Provides
    public InputMethodManager provideInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Singleton
    @Provides
    public PackageManager providePackageManager(Context context) {
        return context.getPackageManager();
    }

    @Singleton
    @Provides
    public ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Singleton
    @Provides
    public AccountManager provideAccountManager(Context context) {
        return AccountManager.get(context);
    }
}
