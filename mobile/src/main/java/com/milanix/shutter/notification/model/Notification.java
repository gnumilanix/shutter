package com.milanix.shutter.notification.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Notification data object
 *
 * @author milan
 */
public class Notification extends RealmObject {
    public static final String FIELD_ID = "id";
    @PrimaryKey
    private String id;
    private String icon;
    private String title;
    private String message;
    private long time = 0L;
    private boolean isRead = false;

    public Notification() {
    }

    public Notification(Notification other) {
        this(other.id, other.icon, other.title, other.message, other.time, other.isRead);
    }

    public Notification(String id, String icon, String title, String message, long time, boolean isRead) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.message = message;
        this.time = time;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead() {
        return isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
