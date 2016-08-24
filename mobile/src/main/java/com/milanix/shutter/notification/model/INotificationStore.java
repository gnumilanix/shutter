package com.milanix.shutter.notification.model;

import com.milanix.shutter.core.IStore;

import java.util.List;

/**
 * Interface to be implemented by notifications stores
 *
 * @author milan
 */
public interface INotificationStore extends IStore {

    void getNotification(String notificationId, Callback<Notification> callback);

    void getNotifications(Callback<List<Notification>> callback);

    void putNotifications(List<Notification> notifications, Callback<List<Notification>> callback);

    void putNotification(Notification notification, Callback<Notification> callback);
}
