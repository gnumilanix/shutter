package com.milanix.shutter.feed.model;

import com.milanix.shutter.core.specification.IFirebaseModel;

import org.parceler.Parcel;

@Parcel
public class Comment implements IFirebaseModel {
    private String id;
    private long time;
    private String comment;
    private Author author;

    public Comment() {
    }

    public Comment(String id, long time, String comment, Author author) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.author = author;
    }

    @Override
    public String key() {
        return id;
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                ", author=" + author +
                '}';
    }
}
