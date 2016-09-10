package com.milanix.shutter.notification.list;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Notification list presenter
 *
 * @author milan
 */
public class NotificationListPresenter extends AbstractPresenter<NotificationListContract.View> implements NotificationListContract.Presenter {
    private final FirebaseUser user;
    private final FirebaseDatabase database;
    private final Query query;

    @Inject
    public NotificationListPresenter(NotificationListContract.View view, FirebaseUser user, FirebaseDatabase database) {
        super(view);
        this.user = user;
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
    public void markRead(String notificationId) {
        final Map<String, Object> updates = new HashMap<>();
        updates.put("/activities/" + user.getUid() + "/" + notificationId + "/read/", true);

        database.getReference().updateChildren(updates);
    }

    @Override
    public void refreshNotification() {
        view.hideProgress();
    }
}
