package com.milanix.shutter.notification;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.notification.model.Notification;

import javax.inject.Inject;

/**
 * Notification list presenter
 *
 * @author milan
 */
public class NotificationListPresenter extends AbstractPresenter<NotificationListContract.View> implements NotificationListContract.Presenter {
    private final FirebaseUser user;
    private final FirebaseDatabase database;

    @Inject
    public NotificationListPresenter(NotificationListContract.View view, FirebaseUser user,
                                     FirebaseDatabase database) {
        super(view);
        this.user = user;
        this.database = database;
    }

    @Override
    public void getNotifications() {
    }

    @Override
    public void markRead(Notification notification) {
    }
}
