package com.milanix.shutter.user.account;

import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.milanix.shutter.core.IStore;

/**
 * Interface to be implemented by account stores
 *
 * @author milan
 */
public interface IAccountStore extends IStore {
    Account getDefaultAccount();

    boolean hasAccount(Account account);

    @Nullable
    String getCachedAuthToken(Account account);

    @WorkerThread
    String getAuthToken(Account account) throws Exception;

    void getAuthToken(Account account, final IStore.Callback<String> callback);

    void invalidateAuthToken(Account account, String token);

    void putAccount(@NonNull String username, @NonNull String password, @NonNull String authToken,
                    @NonNull String refreshToken);

    @WorkerThread
    boolean deleteAccount(Account account);

    void deleteAccount(Account account, IStore.Callback<Void> callback);
}
