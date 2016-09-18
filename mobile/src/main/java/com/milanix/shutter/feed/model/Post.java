package com.milanix.shutter.feed.model;

import com.milanix.shutter.core.specification.IFirebaseModel;

import org.parceler.Parcel;

import java.util.HashMap;


/**
 * Post model
 *
 * @author milan
 */
@Parcel
public class Post implements IFirebaseModel {
    public String postId;
    public String title;
    public String description;
    public String thumbnail;
    public String image;
    public Author author;
    public long time;
    public HashMap<String, Boolean> favoriters = new HashMap<>();
    public HashMap<String, Boolean> viewers = new HashMap<>();
    public HashMap<String, Comment> comments = new HashMap<>();

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

    public Author getAuthor() {
        return author;
    }

    public long getTime() {
        return time;
    }

    public HashMap<String, Boolean> getFavoriters() {
        return favoriters;
    }

    public HashMap<String, Boolean> getViewers() {
        return viewers;
    }

    public HashMap<String, Comment> getComments() {
        return comments;
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
                ", author=" + author +
                ", time=" + time +
                ", favoriters=" + favoriters +
                ", viewers=" + viewers +
                ", comments=" + comments +
                '}';
    }
}
