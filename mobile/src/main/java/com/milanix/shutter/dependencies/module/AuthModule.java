package com.milanix.shutter.dependencies.module;

import android.accounts.AccountManager;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.milanix.shutter.R;
import com.milanix.shutter.user.account.AccountStore;
import com.milanix.shutter.user.account.IAccountStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing auth related dependencies
 *
 * @author milan
 */
@Module
public class AuthModule {

    @Provides
    @Singleton
    public IAccountStore provideAccountStore(AccountManager accountManager) {
        return new AccountStore(accountManager);
    }

    @Provides
    public GoogleSignInOptions provideGoogleSignInOptions(Context context) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }
}
