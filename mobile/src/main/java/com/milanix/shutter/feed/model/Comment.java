package com.milanix.shutter.feed.model;

import com.milanix.shutter.user.model.User;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Feed comment data object
 *
 * @author milan
 */
public class Comment extends RealmObject {
    public static final String FIELD_ID = "id";

    @PrimaryKey
    private long id = -1;
    private long time;
    private User user;
    private String comment;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id == comment.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", time=" + time +
                ", user=" + user +
                ", comment='" + comment + '\'' +
                '}';
    }
}
