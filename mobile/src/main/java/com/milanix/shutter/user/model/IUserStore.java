package com.milanix.shutter.user.model;

import com.milanix.shutter.core.IStore;

/**
 * Interface to be implemented by user stores
 *
 * @author milan
 */
public interface IUserStore extends IStore {
    void getSelf(Callback<User> callback);

    void getUser(String userId, Callback<User> callback);

    void putUser(User user);

    void deleteUser(String userId);
}
