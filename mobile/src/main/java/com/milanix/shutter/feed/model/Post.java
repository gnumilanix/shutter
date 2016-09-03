package com.milanix.shutter.feed.model;

import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.user.model.Profile;

import java.util.HashMap;


/**
 * Post data object
 *
 * @author milan
 */
public class Post implements AbstractFirebaseRecyclerAdapter.FirebaseModel {
    private String postId;
    private String title;
    private String description;
    private String thumbnail;
    private String image;
    private String authorId;
    private Profile author;
    private long createTime;
    private HashMap<String, Boolean> favoriters = new HashMap<>();
    private HashMap<String, Boolean> viewers = new HashMap<>();
    private HashMap<String, Boolean> commenters = new HashMap<>();

    public Post() {
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

    public Profile getAuthor() {
        return author;
    }

    public long getCreateTime() {
        return createTime;
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

        Post post = (Post) o;

        return postId.equals(post.postId);

    }

    @Override
    public int hashCode() {
        return postId.hashCode();
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", authorId='" + authorId + '\'' +
                ", author=" + author +
                ", createTime=" + createTime +
                ", favoriters=" + favoriters +
                ", viewers=" + viewers +
                ", commenters=" + commenters +
                '}';
    }
}
