package com.milanix.shutter.user;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User data object
 *
 * @author milan
 */
public class User extends RealmObject {
    @PrimaryKey
    private long id = -1;
    private String name;
    private String email;

    @Expose(serialize = false, deserialize = true)
    private String token;

    public User() {
    }

    public User(long id, String name, String email, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
