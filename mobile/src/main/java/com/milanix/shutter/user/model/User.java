package com.milanix.shutter.user.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User data object
 *
 * @author milan
 */
public class User extends RealmObject {
    public static final String FIELD_ID = "email";
    private String name;
    @PrimaryKey
    private String email;

    @Expose(serialize = false, deserialize = true)
    private String token;

    public User() {
    }

    public User(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
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

        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
