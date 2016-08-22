package com.milanix.shutter.user.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User data object
 *
 * @author milan
 */
public class User extends RealmObject {
    public static final String FIELD_ID = "email";
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("full_name")
    private String fullName;
    private String avatar;
    @PrimaryKey
    private String email;

    public User() {
    }

    public User(String firstName, String lastName, String fullName, String avatar, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
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
