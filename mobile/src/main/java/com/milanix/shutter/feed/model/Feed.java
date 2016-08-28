package com.milanix.shutter.feed.model;

import com.milanix.shutter.user.model.User;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Feed data object
 *
 * @author milan
 */
public class Feed extends RealmObject {
    public static final String FIELD_ID = "id";

    @PrimaryKey
    private long id = -1;
    private String title;
    private String description;
    private String thumbnail;
    private String image;
    private int views;
    private User author;
    private RealmList<Comment> comments;
    private RealmList<Favorite> favorites;

    public Feed() {
        this.comments = new RealmList<>();
        this.favorites = new RealmList<>();
    }

    public Feed(long id, String title, String description, String thumbnail, String image, int views,
                User author, RealmList<Comment> comments, RealmList<Favorite> favorites) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.image = image;
        this.views = views;
        this.author = author;
        this.comments = comments;
        this.favorites = favorites;
    }

    public long getId() {
        return id;
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

    public int getViews() {
        return views;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feed feed = (Feed) o;

        return id == feed.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", views=" + views +
                ", author=" + author +
                ", comments=" + comments +
                ", favorites=" + favorites +
                '}';
    }
}
