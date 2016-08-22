package com.milanix.shutter.user.model;

import com.milanix.shutter.core.RestCallback;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Store that provide user data from remote data source
 *
 * @author milan
 */
public class RemoteUserStore implements IUserStore {
    private final UserApi userApi;

    @Inject
    public RemoteUserStore(UserApi userApi) {
        this.userApi = userApi;
    }


    @Override
    public void getSelf(final Callback<User> callback) {
        userApi.getSelf().enqueue(new RestCallback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getUser(String userId, final Callback<User> callback) {
        userApi.getUser(userId).enqueue(new RestCallback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void putUser(User user) {
        throw new UnsupportedOperationException("Put operation not supported in this store");
    }

    @Override
    public void deleteUser(String userId) {
        throw new UnsupportedOperationException("Delete operation not supported in this store");
    }
}
