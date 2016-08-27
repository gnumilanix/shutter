package com.milanix.shutter.user.model;

import com.android.annotations.Nullable;

/**
 * Contract for classes acting as user repository
 *
 * @author milan
 */
public interface IUserRepository extends IUserStore {
    void refreshSelf(@Nullable Callback<User> callback);

    void refreshUser(String userId, @Nullable Callback<User> callback);
}
