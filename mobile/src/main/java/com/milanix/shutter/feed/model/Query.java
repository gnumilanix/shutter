package com.milanix.shutter.feed.model;

import android.support.annotation.IntRange;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.milanix.shutter.feed.model.Query.Type.PUBLIC;
import static com.milanix.shutter.feed.model.Query.Type.SELF;
import static com.milanix.shutter.feed.model.Query.Type.USER;

/**
 * Query class for performing complex {@link Feed} query
 *
 * @author milan
 */
public class Query {
    private final String type;
    private final int page;
    private final int count;
    private final boolean favorite;

    @StringDef({SELF, PUBLIC, USER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        String SELF = "SELF";
        String USER = "USER";
        String PUBLIC = "PUBLIC";
    }

    private Query(@Type String type, @IntRange(from = 0) int page, @IntRange(from = 0, to = 60) int count, boolean favorite) {
        this.type = type;
        this.page = page;
        this.count = count;
        this.favorite = favorite;
    }

    public static class Builder {
        private String type = Query.Type.SELF;
        private int page = 0;
        private int count = 20;
        private boolean favorite = false;

        public Builder setType(@Type String type) {
            this.type = type;
            return this;
        }

        public Builder setPage(@IntRange(from = 0) int page) {
            this.page = page;
            return this;
        }

        public Builder setCount(@IntRange(from = 0, to = 60) int count) {
            this.count = count;
            return this;
        }

        public Builder setFavorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Query build() {
            return new Query(type, page, count, favorite);
        }
    }
}