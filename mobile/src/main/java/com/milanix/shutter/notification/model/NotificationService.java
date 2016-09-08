package com.milanix.shutter.notification.model;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.milanix.shutter.App;
import com.milanix.shutter.R;
import com.milanix.shutter.user.UserComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link FirebaseMessagingService} used by this app
 *
 * @author milan
 */
public class NotificationService extends Service implements ChildEventListener {
    private static final List<Notification> NOTIFICATIONS = new ArrayList<>();
    private static final HashMap<String, Integer> NOTIFICATIONS_MAP = new HashMap<>();
    private static final String NOTIFICATION_GROUP = "_notification_group";
    private static final String TAG_NOTIFICATION = "_notification_tag";
    private static final int ID_NOTIFICATION = 100;

    @Inject
    protected FirebaseDatabase database;
    @Inject
    protected FirebaseUser user;
    @Inject
    protected NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        subscribe(((App) getApplicationContext()).getUserComponent());
    }

    private void subscribe(UserComponent component) {
        if (null != component) {
            component.inject(this);

            final Query reference = getNotificationReference(user);

            if (null != reference)
                reference.addChildEventListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    private void unsubscribe() {
        final Query reference = getNotificationReference(user);

        if (null != reference)
            reference.removeEventListener(this);
    }

    @Nullable
    private Query getNotificationReference(FirebaseUser user) {
        if (null != database && null != user)
            return database.getReference().child("activities").child(user.getUid()).orderByChild("time");
        else
            return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        generateNotification(dataSnapshot.getValue(Notification.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private void generateNotification(@Nullable Notification incomingNotification) {
        if (null != incomingNotification && !incomingNotification.isRead()) {
            NOTIFICATIONS.add(incomingNotification);
            final String recentNotificationMessage = getMessage(incomingNotification);
            final Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().
                    setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bg_branding_gradient));
            final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle().
                    setBigContentTitle(getString(R.string.notification_interactions_title)).
                    setSummaryText(getResources().getQuantityString(R.plurals.interactions,
                            NOTIFICATIONS.size(), NOTIFICATIONS.size()));

            for (int i = 0, size = NOTIFICATIONS.size(); i < size; i++) {
                final Notification notification = NOTIFICATIONS.get(i);
                final String notificationMessage = getMessage(notification);

                style.addLine(notificationMessage);

                final NotificationCompat.BigTextStyle wearNotification = new NotificationCompat.BigTextStyle().
                        bigText(notificationMessage);

                final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle(getString(R.string.notification_interactions_title))
                        .setContentText(recentNotificationMessage)
                        .setSmallIcon(R.drawable.ic_tab_notifications)
                        .setLargeIcon(largeIcon)
                        .setStyle(wearNotification)
                        .setAutoCancel(true)
                        .setGroup(NOTIFICATION_GROUP)
                        .setDefaults(android.app.Notification.DEFAULT_LIGHTS);

                notificationManager.notify(TAG_NOTIFICATION, ID_NOTIFICATION + i,
                        notificationBuilder.build());
            }

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.notification_interactions_title))
                    .setContentText(recentNotificationMessage)
                    .setSmallIcon(R.drawable.ic_tab_notifications)
                    .setLargeIcon(largeIcon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setGroup(NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_EVENT)
                    .extend(wearableExtender);
            notificationManager.notify(TAG_NOTIFICATION, ID_NOTIFICATION,
                    notificationBuilder.build());
        }
    }

    @Nullable
    public String getMessage(@NonNull Notification notification) {
        switch (notification.getType()) {
            case Notification.Type.COMMENT:
                return getString(R.string.message_notification_comment, notification.getAuthor().getName());
            case Notification.Type.FOLLOW:
                return getString(R.string.message_notification_following, notification.getAuthor().getName());
            case Notification.Type.FAVORITE:
                return getString(R.string.message_notification_favorite, notification.getAuthor().getName());
            case Notification.Type.NEWS:
                return notification.getMessage();
            default:
                return null;
        }
    }
}
