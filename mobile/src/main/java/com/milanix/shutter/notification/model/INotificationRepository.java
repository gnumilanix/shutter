package com.milanix.shutter.notification.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Contract for classes acting as notification repository
 *
 * @author milan
 */
public interface INotificationRepository extends INotificationStore {
    void refreshNotifications(@Nullable Callback<List<Notification>> callback);
}
