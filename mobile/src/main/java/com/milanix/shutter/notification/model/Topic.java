package com.milanix.shutter.notification.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Topic model
 *
 * @author milan
 */
public class Topic {
    private final List<String> topics;

    public Topic() {
        topics = new ArrayList<>();
    }

    public List<String> getTopics() {
        return topics;
    }
}
