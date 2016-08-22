package com.milanix.shutter.feed.model;

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

    public Feed() {
    }

    public Feed(long id, String title, String description, String thumbnail, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.image = image;
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
}
