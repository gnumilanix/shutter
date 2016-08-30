package com.milanix.shutter.feed.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User schema
 *
 * @author milan
 */
public class User {
    public String userId;
    public String email;
    public String avatar;
    public String firstName;
    public String lastName;
    public String fullName;
    public List<Follower> followers;
    public List<Follower> followings;
    public List<Post> posts;

    public User() {
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        posts = new ArrayList<>();
    }

    public class Follower {
        public String followerId;
    }

    public class Following {
        public String followingId;
    }

    public class Post {
        public String postId;
    }
}
