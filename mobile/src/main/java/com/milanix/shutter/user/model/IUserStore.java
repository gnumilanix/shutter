package com.milanix.shutter.user.model;

import com.google.firebase.auth.UserInfo;
import com.milanix.shutter.core.specification.IStore;

/**
 * Interface to be implemented by user stores
 *
 * @author milan
 */
public interface IUserStore extends IStore {
    void getSelf(Callback<UserInfo> callback);

    void getUser(String userId, Callback<User> callback);

    void putUser(User user);

    void deleteUser(String userId);
}
