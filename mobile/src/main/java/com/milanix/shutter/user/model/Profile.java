package com.milanix.shutter.user.model;

import com.google.firebase.database.Exclude;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Profile schema
 *
 * @author milan
 */
public class Profile implements AbstractFirebaseRecyclerAdapter.FirebaseModel {
    public String userId;
    public String email;
    public String avatar;
    public String fullName;
    public HashMap<String, Boolean> followers = new HashMap<>();
    public HashMap<String, Boolean> followings = new HashMap<>();
    public HashMap<String, Boolean> posts = new HashMap<>();
    public HashMap<String, Boolean> favorites = new HashMap<>();
    public HashMap<String, Boolean> views = new HashMap<>();

    public Profile() {

    }

    public Profile(String userId, String email, String avatar, String fullName) {
        this.userId = userId;
        this.email = email;
        this.avatar = avatar;
        this.fullName = fullName;
    }

    @Exclude
    public Map<String, Object> toCreateProfileMap() {
        final HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("email", email);
        result.put("avatar", avatar);
        result.put("fullName", fullName);

        return result;
    }

    @Override
    public String key() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        return userId.equals(profile.userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
