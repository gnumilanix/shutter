package com.milanix.shutter.user.auth;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.RestCallback;
import com.milanix.shutter.user.account.IAccountStore;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

import static com.milanix.shutter.BuildConfig.CLIENT_ID;
import static com.milanix.shutter.BuildConfig.CLIENT_SECRET;
import static com.milanix.shutter.user.account.AccountAuthenticator.GRANT_PASSWORD;
import static com.milanix.shutter.user.account.AccountAuthenticator.GRANT_REFRESH_TOKEN;

/**
 * Store that provide authentication from remote data source
 *
 * @author milan
 */
public class OAuthStore implements IAuthStore {
    private final AuthApi api;
    private IAccountStore accountStore;

    @Inject
    public OAuthStore(AuthApi api, IAccountStore accountStore) {
        this.api = api;
        this.accountStore = accountStore;
    }

    @Override
    public void initExistingSession(Callback<Authorization> callback) {
        try {
            accountStore.getCachedAuthToken(accountStore.getDefaultAccount());
        } catch (Exception e) {
            Timber.i(e, "Failed to retrieve cached token");
            callback.onFailure(e);
        }

        new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Timber.d("onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Timber.d("onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void signUp(final String username, final String password, String email, String firstName, String lastName,
                       final Callback<Authorization> callback) {
        api.signUp(username, password, email, firstName, lastName).
                enqueue(new RestCallback<Authorization>() {
                    @Override
                    public void onResponse(Response<Authorization> response) {
                        final Authorization authorization = response.body();
                        accountStore.putAccount(username, password, authorization.getAccessToken(), authorization.getRefreshToken());
                        callback.onSuccess(authorization);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Timber.i(t, "Failed to sign up");
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public void signIn(final String username, final String password, final Callback<Authorization> callback) {
        api.authorize(username, password, GRANT_PASSWORD, CLIENT_ID, CLIENT_SECRET).
                enqueue(new RestCallback<Authorization>() {
                    @Override
                    public void onResponse(Response<Authorization> response) {
                        final Authorization authorization = response.body();
                        accountStore.putAccount(username, password, authorization.getAccessToken(), authorization.getRefreshToken());
                        callback.onSuccess(authorization);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Timber.i(t, "Failed to sign in");
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public Authorization signIn(String username, String password) throws Exception {
        final Authorization authorization = api.authorize(username, password, GRANT_PASSWORD, CLIENT_ID, CLIENT_SECRET).execute().body();
        accountStore.putAccount(username, password, authorization.getAccessToken(), authorization.getRefreshToken());

        return authorization;
    }

    @Override
    public Authorization refreshToken(String refreshToken) throws Exception {
        return api.refreshToken(GRANT_REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET, refreshToken).execute().body();
    }

    @Override
    public void logout(final Callback<Void> callback) {
        api.logout(accountStore.getCachedAuthToken(accountStore.getDefaultAccount())).
                enqueue(new RestCallback<ResponseBody>() {
                    @Override
                    public void onResponse(Response<ResponseBody> response) {
                        deleteAccount(callback);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Timber.i(t, "Failed to log out");
                        deleteAccount(callback);
                    }
                });
    }

    private void deleteAccount(final Callback<Void> callback) {
        accountStore.deleteAccount(accountStore.getDefaultAccount(), new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable t) {
                Timber.i(t, "Failed to delete account");
                callback.onSuccess(null);
            }
        });
    }
}
