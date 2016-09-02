package com.milanix.shutter.notification.model;

/**
 * Notification data object
 *
 * @author milan
 */
public class Notification {
    private String id;
    private String avatar;
    private String post;
    private String message;
    private long time = 0L;
    private boolean isRead = false;

    public Notification() {
    }

    public Notification(Notification other) {
        this(other.id, other.avatar, other.post, other.message, other.time, other.isRead);
    }

    public Notification(String id, String avatar, String post, String message, long time, boolean isRead) {
        this.id = id;
        this.avatar = avatar;
        this.post = post;
        this.message = message;
        this.time = time;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPost() {
        return post;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
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

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", post='" + post + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", isRead=" + isRead +
                '}';
    }
}
