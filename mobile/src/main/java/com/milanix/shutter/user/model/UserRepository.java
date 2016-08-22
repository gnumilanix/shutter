package com.milanix.shutter.user.model;

import com.android.annotations.Nullable;
import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.qualifier.Remote;

/**
 * Repository that provide user data
 *
 * @author milan
 */
public class UserRepository implements IUserRepository {
    private final IUserStore localStore;
    private final IUserStore remoteStore;

    public UserRepository(@Local IUserStore localStore, @Remote IUserStore remoteStore) {
        this.localStore = localStore;
        this.remoteStore = remoteStore;
    }

    @Override
    public void refreshUser(String userId, @Nullable final Callback<User> callback) {
        remoteStore.getUser(userId, new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                localStore.putUser(result);

                if (null != callback)
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getSelf(final Callback<User> callback) {
        localStore.getSelf(new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                remoteStore.getSelf(callback);
            }
        });
    }

    @Override
    public void getUser(final String userId, final Callback<User> callback) {
        localStore.getUser(userId, new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                remoteStore.getUser(userId, callback);
            }
        });
    }

    @Override
    public void putUser(User user) {
        localStore.putUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        localStore.deleteUser(userId);
    }
}
