package com.milanix.shutter.notification.model;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

import static com.milanix.shutter.core.RestCallback.invokeCallback;

/**
 * Store that provide notification data from local data source
 *
 * @author milan
 */
public class LocalNotificationStore implements INotificationStore {
    @Inject
    public LocalNotificationStore() {
    }

    @Override
    public void getNotification(String notificationId, Callback<Notification> callback) {
        invokeCallback(Realm.getDefaultInstance().where(Notification.class).equalTo(Notification.FIELD_ID, notificationId).findFirst(), callback);
    }

    @Override
    public void getNotifications(Callback<List<Notification>> callback) {
        invokeCallback(Realm.getDefaultInstance().where(Notification.class).findAll(), callback);
    }

    @Override
    public void putNotifications(List<Notification> notifications, Callback<List<Notification>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(notifications);
        realm.commitTransaction();

        if (null != callback)
            callback.onSuccess(null);
    }

    @Override
    public void putNotification(Notification notification, Callback<Notification> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(notification);
        realm.commitTransaction();

        if (null != callback)
            callback.onSuccess(null);
    }
}
