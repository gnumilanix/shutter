package com.milanix.shutter.feed.model;

import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;

import java.util.HashMap;

import io.realm.annotations.PrimaryKey;

/**
 * Feed data object
 *
 * @author milan
 */
public class Feed implements AbstractFirebaseRecyclerAdapter.FirebaseModel {
    public static final String FIELD_ID = "id";

    @PrimaryKey
    private String postId;
    private String title;
    private String description;
    private String thumbnail;
    private String image;
    private String authorId;
    private HashMap<String, Boolean> favoriters = new HashMap<>();
    private HashMap<String, Boolean> viewers = new HashMap<>();
    private HashMap<String, Boolean> commenters = new HashMap<>();

    public Feed() {
    }

    @Override
    public String key() {
        return postId;
    }

    public String getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getImage() {
        return image;
    }

    public String getAuthorId() {
        return authorId;
    }

    public HashMap<String, Boolean> getFavoriters() {
        return favoriters;
    }

    public HashMap<String, Boolean> getViewers() {
        return viewers;
    }

    public HashMap<String, Boolean> getCommenters() {
        return commenters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feed feed = (Feed) o;

        return postId.equals(feed.postId);

    }

    @Override
    public int hashCode() {
        return postId.hashCode();
    }

    @Override
    public String toString() {
        return "Feed{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", authorId='" + authorId + '\'' +
                ", favoriters=" + favoriters +
                ", viewers=" + viewers +
                ", commenters=" + commenters +
                '}';
    }
}
