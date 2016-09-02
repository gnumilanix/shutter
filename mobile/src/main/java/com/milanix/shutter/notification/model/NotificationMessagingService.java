package com.milanix.shutter.notification.model;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.milanix.shutter.App;
import com.milanix.shutter.user.UserComponent;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link FirebaseMessagingService} used by this app
 *
 * @author milan
 */
public class NotificationMessagingService extends FirebaseMessagingService {
    private static final String KEY_DATA = "data";
    public static final String NOTIFICATIONS = "notifications";

    @Inject
    protected Gson gson;

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

            retrieveNotifications(topics.contains(NOTIFICATIONS));
        }
    }

    private void retrieveNotifications(boolean sync) {

    }
}
