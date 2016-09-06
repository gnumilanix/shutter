package com.milanix.shutter.notification;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.notification.model.Notification;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Notification list presenter
 *
 * @author milan
 */
public class NotificationListPresenter extends AbstractPresenter<NotificationListContract.View> implements NotificationListContract.Presenter {
    private final FirebaseDatabase database;
    private final Query query;

    @Inject
    public NotificationListPresenter(NotificationListContract.View view, FirebaseUser user, FirebaseDatabase database) {
        super(view);
        this.database = database;
        this.query = database.getReference().child("activities").child(user.getUid()).orderByChild("time");
    }

    @Override
    public void subscribe(ChildEventListener notificationEventListener) {
        query.addChildEventListener(notificationEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener notificationEventListener) {
        query.removeEventListener(notificationEventListener);
    }

    @Override
    public void markRead(Notification notification) {
        final Map<String, Object> updates = new HashMap<>();
        updates.put("/activities/" + notification.getId() + "/read/", true);

        database.getReference().updateChildren(updates);
    }

    @Override
    public void refreshNotification() {
        view.hideProgress();
    }
}
