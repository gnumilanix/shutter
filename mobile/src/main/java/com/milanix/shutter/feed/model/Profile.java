package com.milanix.shutter.feed.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Profile schema
 *
 * @author milan
 */
public class Profile {
    public String userId;
    public String email;
    public String avatar;
    public String fullName;
    public List<Follower> followers = new ArrayList<>();
    public List<Follower> followings = new ArrayList<>();
    public List<Post> posts = new ArrayList<>();

    public Profile() {

    }

    public Profile(String userId, String email, String avatar, String fullName, List<Follower> followers, List<Follower> followings, List<Post> posts) {
        this.userId = userId;
        this.email = email;
        this.avatar = avatar;
        this.fullName = fullName;
        this.followers = followers;
        this.followings = followings;
        this.posts = posts;
    }

    public static class Follower {
        public String followerId;

        public Follower() {
        }
    }

    public static class Following {
        public String followingId;

        public Following() {
        }
    }

    public static class Post {
        public String postId;

        public Post() {
        }
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
}
