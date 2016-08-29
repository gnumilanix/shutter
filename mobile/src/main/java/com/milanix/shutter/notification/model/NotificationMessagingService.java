package com.milanix.shutter.notification.model;

import com.android.annotations.Nullable;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.milanix.shutter.App;
import com.milanix.shutter.core.JobScheduler;
import com.milanix.shutter.user.UserComponent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of {@link FirebaseMessagingService} used by this app
 *
 * @author milan
 */
public class NotificationMessagingService extends FirebaseMessagingService {
    private static final String KEY_DATA = "data";
    public static final String FEEDS = "feeds";
    public static final String NOTIFICATIONS = "notifications";

    @Inject
    protected Gson gson;
    @Inject
    @Named(NOTIFICATIONS)
    protected Job notificationSyncJob;
    @Inject
    protected JobScheduler jobScheduler;


    @Override
    public void onCreate() {
        super.onCreate();
        inject(((App) getApplicationContext()).getUserComponent());
    }

    private void inject(UserComponent component) {
        if (null != component)
            component.inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        handleDataEvent(remoteMessage.getData().get(KEY_DATA));
    }

    private void handleDataEvent(String data) {
        final Topic topic = gson.fromJson(data, Topic.class);

        if (null != topic) {
            final List<String> topics = topic.getTopics();

            scheduleJob(topics.contains(NOTIFICATIONS), notificationSyncJob);
        }
    }

    private void scheduleJob(boolean sync, @Nullable Job job) {
        if (sync && null != jobScheduler && null != job)
            jobScheduler.schedule(job);
    }
}
