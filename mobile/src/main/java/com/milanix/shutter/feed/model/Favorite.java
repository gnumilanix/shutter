package com.milanix.shutter.feed.model;

import com.milanix.shutter.user.model.User;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Feed favorite data object
 *
 * @author milan
 */
public class Favorite extends RealmObject {
    public static final String FIELD_ID = "id";

    @PrimaryKey
    private long id = -1;
    private long time;
    private User user;

    public Favorite() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Favorite favorite = (Favorite) o;

        return id == favorite.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", time=" + time +
                ", user=" + user +
                '}';
    }
}
