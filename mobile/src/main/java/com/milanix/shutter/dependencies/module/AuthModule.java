package com.milanix.shutter.dependencies.module;

import android.accounts.AccountManager;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.milanix.shutter.R;
import com.milanix.shutter.user.account.AccountStore;
import com.milanix.shutter.user.account.IAccountStore;
import com.milanix.shutter.user.auth.AuthApi;
import com.milanix.shutter.user.auth.IAuthStore;
import com.milanix.shutter.user.auth.OAuthStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module providing auth related dependencies
 *
 * @author milan
 */
@Module
public class AuthModule {

    @Provides
    @Singleton
    public AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Singleton
    public IAuthStore provideAuthStore(AuthApi api, IAccountStore provider) {
        return new OAuthStore(api, provider);
    }

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
