package com.milanix.shutter.notification.model;

import android.support.annotation.StringDef;

import com.milanix.shutter.core.specification.IFirebaseModel;
import com.milanix.shutter.feed.model.Author;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.milanix.shutter.notification.model.Notification.Type.COMMENT;
import static com.milanix.shutter.notification.model.Notification.Type.FAVORITE;
import static com.milanix.shutter.notification.model.Notification.Type.FOLLOW;
import static com.milanix.shutter.notification.model.Notification.Type.NEWS;
import static com.milanix.shutter.notification.model.Notification.Type.UNFAVORITE;
import static com.milanix.shutter.notification.model.Notification.Type.UNFOLLOW;

/**
 * Notification model
 *
 * @author milan
 */
public class Notification implements IFirebaseModel {
    @StringDef({FOLLOW, UNFOLLOW, FAVORITE, UNFAVORITE, COMMENT, NEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        String FOLLOW = "follow";
        String UNFOLLOW = "un-follow";
        String FAVORITE = "favorite";
        String UNFAVORITE = "un-favorite";
        String COMMENT = "comment";
        String NEWS = "news";
    }

    private String id;
    @Type
    private String type;
    private Author author;
    private Post post;
    private String message;
    private long time = 0L;
    private boolean read = false;

    public Notification() {
    }

    public Notification(boolean read, long time, String message, Post post, Author author, String type, String id) {
        this.read = read;
        this.time = time;
        this.message = message;
        this.post = post;
        this.author = author;
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Author getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", post='" + post + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", read=" + read +
                '}';
    }

    @Override
    public String key() {
        return id;
    }

    public static class Post implements IFirebaseModel {
        private String id;
        private String image;

        public Post() {
        }

        public Post(com.milanix.shutter.feed.model.Post post) {
            this(post.getPostId(), post.getImage());
        }

        public Post(String id, String image) {
            this.id = id;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Post post = (Post) o;

            return id.equals(post.id);

        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return "Post{" +
                    "id='" + id + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }

        @Override
        public String key() {
            return id;
        }
    }
}
