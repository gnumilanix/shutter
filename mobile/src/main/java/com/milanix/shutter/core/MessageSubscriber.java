package com.milanix.shutter.core;

import android.support.annotation.Size;

import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

/**
 * Class that subscribes/un-subcribes to topics
 *
 * @author milan
 */
public class MessageSubscriber {

    @Inject
    public MessageSubscriber() {
    }

    /**
     * Subscribe to given topics
     *
     * @param topics to subscribe to
     */
    public void subscribe(@Size(min = 1) String... topics) {
        for (String topic : topics) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
        }
    }

    /**
     * Un-subscribes from given topics
     *
     * @param topics to un-subscribe from
     */
    public void unsubscribe(@Size(min = 1) String... topics) {
        for (String topic : topics) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        }
    }
}
