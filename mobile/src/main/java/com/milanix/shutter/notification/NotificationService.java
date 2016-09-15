package com.milanix.shutter.notification;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.firebase.jobdispatcher.Job;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.milanix.shutter.App;
import com.milanix.shutter.R;
import com.milanix.shutter.core.JobScheduler;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;
import com.milanix.shutter.home.HomeActivity;
import com.milanix.shutter.notification.model.Notification;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of {@link FirebaseMessagingService} used by this app
 *
 * @author milan
 */
public class NotificationService extends Service implements ChildEventListener {
    public static final String NOTIFICATIONS = "notifications";

    private static final String GROUP_FOLLOWERS = "_follower_group";
    private static final String GROUP_COMMENTS = "_comment_group";
    private static final String GROUP_FAVORITES = "_favorite_group";
    private static final String GROUP_INTERACTION = "interaction_group";
    private static final String TAG_FOLLOWERS = "followers_tag";
    private static final String TAG_COMMENTS = "comments_tag";
    private static final String TAG_COMMENT_PREFIX = "comment_tag_";
    private static final String TAG_FAVORITES = "favorites_tag";
    private static final String TAG_FAVORITE_PREFIX = "favorite_tag_";
    private static final int ID_FOLLOWERS = 76;

    private final List<Notification> FOLLOWING_NOTIFICATIONS = new ArrayList<>();
    private final HashMap<String, List<Notification>> FAVORITE_NOTIFICATIONS = new HashMap<>();
    private final HashMap<String, List<Notification>> COMMENT_NOTIFICATIONS = new HashMap<>();

    @Inject
    protected FirebaseDatabase database;
    @Inject
    protected FirebaseUser user;
    @Inject
    protected JobScheduler jobScheduler;
    @Inject
    @Named(NotificationModule.NOTIFICATION_JOB)
    protected Job notificationJob;
    @Inject
    protected NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        subscribe(((App) getApplicationContext()).getUserComponent());
    }

    private void subscribe(UserComponent component) {
        if (null != component) {
            component.with(new NotificationModule()).inject(this);
            jobScheduler.schedule(notificationJob);

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
            return database.getReference().child("activities").child(user.getUid()).orderByChild("notified").equalTo(false);
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
        regenerateNotification(dataSnapshot.getValue(Notification.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        cancelNotification(dataSnapshot.getValue(Notification.class));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /**
     * Cancels existing notification before generating new one
     *
     * @param notification to regenerate
     */
    private void regenerateNotification(Notification notification) {
        cancelNotification(notification);
        generateNotification(notification);
    }

    /**
     * Generates notification based on type
     *
     * @param notification to generate
     */
    private void generateNotification(@Nullable Notification notification) {
        if (null != user && null != notification) {
            switch (notification.getType()) {
                case Notification.Type.FOLLOW:
                    generateFollowingNotification(notification);
                    break;
                case Notification.Type.FAVORITE:
                    generateFavoriteNotification(notification);
                    break;
                case Notification.Type.COMMENT:
                    generateCommentNotification(notification);
                    break;
                default:
                    generateNewsNotification(notification);
            }

            markNotified(notification);
        }
    }

    private void markNotified(@NonNull Notification notification) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/activities/" + user.getUid() + "/" + notification.getId() + "/notified", true);

        database.getReference().updateChildren(update);
    }

    /**
     * Cancels existing notification
     *
     * @param notification to cancel
     */
    private void cancelNotification(Notification notification) {
        if (null != notification) {
            switch (notification.getType()) {
                case Notification.Type.FOLLOW:
                    cancelFollowingNotification(notification);
                    break;
                case Notification.Type.FAVORITE:
                    cancelFavoriteNotification(notification);
                    break;
                case Notification.Type.COMMENT:
                    cancelCommentNotification(notification);
                    break;
            }

            notificationManager.cancel(notification.getId(), notification.getId().hashCode());
        }
    }

    /**
     * Generates following group notification
     *
     * @param notification to add and generate
     */
    private void generateFollowingNotification(@NonNull Notification notification) {
        FOLLOWING_NOTIFICATIONS.add(notification);

        final Intent notificationIntent = new Intent(this, ProfileActivity.class).setAction(ProfileActivity.Tab.FOLLOWERS).
                putExtra(ProfileModule.PROFILE_ID, user.getUid());
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this).addNextIntentWithParentStack(notificationIntent);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        final String recentNotificationMessage = getMessage(notification);
        final String recentNotificationTitle = getResources().getQuantityString(R.plurals.new_followers,
                FOLLOWING_NOTIFICATIONS.size(), FOLLOWING_NOTIFICATIONS.size());
        final Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().
                setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bg_branding_gradient));
        final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle().
                setBigContentTitle(getString(R.string.notification_followers_title)).
                setSummaryText(recentNotificationTitle);

        for (int i = 0, size = FOLLOWING_NOTIFICATIONS.size(); i < size; i++) {
            final Notification notificationItem = FOLLOWING_NOTIFICATIONS.get(i);
            final String notificationMessage = getMessage(notificationItem);
            final NotificationCompat.BigTextStyle wearNotification = new NotificationCompat.BigTextStyle().
                    bigText(notificationMessage);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.notification_followers_title))
                    .setContentText(recentNotificationMessage)
                    .setSmallIcon(R.drawable.ic_notification_followers)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setStyle(wearNotification)
                    .setAutoCancel(true)
                    .setGroup(GROUP_FOLLOWERS)
                    .setDefaults(android.app.Notification.DEFAULT_LIGHTS);

            notificationManager.notify(notificationItem.getId(), notificationItem.getId().hashCode(),
                    notificationBuilder.build());

            style.addLine(notificationMessage);
        }

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(recentNotificationTitle)
                .setContentText(recentNotificationMessage)
                .setSmallIcon(R.drawable.ic_notification_followers)
                .setLargeIcon(largeIcon)
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setAutoCancel(true)
                .setOngoing(false)
                .setGroup(GROUP_FOLLOWERS)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .extend(wearableExtender);
        notificationManager.notify(TAG_FOLLOWERS, ID_FOLLOWERS, notificationBuilder.build());
    }

    private void cancelFollowingNotification(@NonNull Notification notification) {
        FOLLOWING_NOTIFICATIONS.remove(notification);
        notificationManager.cancel(TAG_FOLLOWERS, ID_FOLLOWERS);
    }

    private void generateFavoriteNotification(Notification notification) {
        final String recentPostId = notification.getPost().getId();

        if (!FAVORITE_NOTIFICATIONS.containsKey(recentPostId))
            FAVORITE_NOTIFICATIONS.put(recentPostId, new ArrayList<Notification>());

        final List<Notification> notifications = FAVORITE_NOTIFICATIONS.get(recentPostId);
        notifications.add(notification);

        final Intent notificationIntent = new Intent(this, PostDetailActivity.class).putExtra(PostModule.POST_ID, recentPostId);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this).addNextIntentWithParentStack(notificationIntent);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        final String recentNotificationMessage = getMessage(notification);
        final String recentNotificationTitle = getResources().getQuantityString(R.plurals.new_favorites,
                notifications.size(), notifications.size());
        final Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().
                setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bg_branding_gradient));
        final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle().
                setBigContentTitle(getString(R.string.notification_favorites_title)).
                setSummaryText(recentNotificationTitle);

        for (int i = 0, size = notifications.size(); i < size; i++) {
            final Notification notificationItem = notifications.get(i);
            final String postId = notificationItem.getPost().getId();
            final String notificationMessage = getMessage(notificationItem);
            final NotificationCompat.BigTextStyle wearNotification = new NotificationCompat.BigTextStyle().
                    bigText(notificationMessage);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.notification_favorites_title))
                    .setContentText(recentNotificationMessage)
                    .setSmallIcon(R.drawable.ic_notification_favorite)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setStyle(wearNotification)
                    .setAutoCancel(true)
                    .setGroup(GROUP_FAVORITES)
                    .setDefaults(android.app.Notification.DEFAULT_LIGHTS);

            notificationManager.notify(TAG_FAVORITE_PREFIX + postId, postId.hashCode(), notificationBuilder.build());

            style.addLine(notificationMessage);
        }

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(recentNotificationTitle)
                .setContentText(recentNotificationMessage)
                .setSmallIcon(R.drawable.ic_notification_favorite)
                .setLargeIcon(largeIcon)
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setAutoCancel(true)
                .setOngoing(false)
                .setGroup(GROUP_FAVORITES)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .extend(wearableExtender);
        notificationManager.notify(TAG_FAVORITES, recentPostId.hashCode(), notificationBuilder.build());
    }

    private void cancelFavoriteNotification(Notification notification) {
        final String postId = notification.getPost().getId();

        if (FAVORITE_NOTIFICATIONS.containsKey(postId))
            FAVORITE_NOTIFICATIONS.get(postId).remove(notification);

        notificationManager.cancel(TAG_FAVORITES, postId.hashCode());
    }

    private void generateCommentNotification(Notification notification) {
        final String recentPostId = notification.getPost().getId();

        if (!COMMENT_NOTIFICATIONS.containsKey(recentPostId))
            COMMENT_NOTIFICATIONS.put(recentPostId, new ArrayList<Notification>());

        final List<Notification> notifications = COMMENT_NOTIFICATIONS.get(recentPostId);
        notifications.add(notification);

        final Intent notificationIntent = new Intent(this, PostDetailActivity.class).putExtra(PostModule.POST_ID, recentPostId);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this).addNextIntentWithParentStack(notificationIntent);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        final String recentNotificationMessage = getMessage(notification);
        final String recentNotificationTitle = getResources().getQuantityString(R.plurals.new_comments,
                notifications.size(), notifications.size());
        final Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().
                setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bg_branding_gradient));
        final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle().
                setBigContentTitle(getString(R.string.notification_comments_title)).
                setSummaryText(recentNotificationTitle);

        for (int i = 0, size = notifications.size(); i < size; i++) {
            final Notification notificationItem = notifications.get(i);
            final String postId = notificationItem.getPost().getId();
            final String notificationMessage = getMessage(notificationItem);
            final NotificationCompat.BigTextStyle wearNotification = new NotificationCompat.BigTextStyle().
                    bigText(notificationMessage);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.notification_comments_title))
                    .setContentText(recentNotificationMessage)
                    .setSmallIcon(R.drawable.ic_notiication_comment)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setStyle(wearNotification)
                    .setAutoCancel(true)
                    .setGroup(GROUP_COMMENTS)
                    .setDefaults(android.app.Notification.DEFAULT_LIGHTS);

            notificationManager.notify(TAG_COMMENT_PREFIX + postId, postId.hashCode(), notificationBuilder.build());

            style.addLine(notificationMessage);
        }

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(recentNotificationTitle)
                .setContentText(recentNotificationMessage)
                .setSmallIcon(R.drawable.ic_notiication_comment)
                .setLargeIcon(largeIcon)
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setAutoCancel(true)
                .setOngoing(false)
                .setGroup(GROUP_COMMENTS)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .extend(wearableExtender);
        notificationManager.notify(TAG_COMMENTS, recentPostId.hashCode(), notificationBuilder.build());
    }

    private void cancelCommentNotification(Notification notification) {
        final String postId = notification.getPost().getId();

        if (COMMENT_NOTIFICATIONS.containsKey(postId))
            COMMENT_NOTIFICATIONS.get(postId).remove(notification);

        notificationManager.cancel(TAG_COMMENTS, postId.hashCode());
    }

    /**
     * Generates per interaction notification
     *
     * @param notification to generate
     */
    private void generateNewsNotification(@NonNull Notification notification) {
        final Intent notificationIntent = new Intent(this, HomeActivity.class).setAction(HomeActivity.Tab.NOTIFICATIONS);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this).addNextIntentWithParentStack(notificationIntent);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        final String recentNotificationMessage = getMessage(notification);
        final Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().
                setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.bg_branding_gradient));
        final NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle().
                setBigContentTitle(getString(R.string.notification_interactions_title)).
                setSummaryText(recentNotificationMessage);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_interactions_title))
                .setContentText(recentNotificationMessage)
                .setSmallIcon(R.drawable.ic_notification_news)
                .setLargeIcon(largeIcon)
                .setStyle(style)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(false)
                .setGroup(GROUP_INTERACTION)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .extend(wearableExtender);
        notificationManager.notify(notification.getId(), notification.getId().hashCode(), notificationBuilder.build());
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
