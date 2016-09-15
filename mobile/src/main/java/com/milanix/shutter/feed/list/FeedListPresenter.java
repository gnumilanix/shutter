package com.milanix.shutter.feed.list;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements FeedListContract.Presenter {
    private Query postsQuery;
    private Lazy<FirebaseDatabase> database;

    @Inject
    public FeedListPresenter(FeedListContract.View view, Lazy<FirebaseDatabase> database) {
        super(view);
        this.database = database;
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        super.subscribe();
        getPostsQuery().addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        super.unsubscribe();
        getPostsQuery().removeEventListener(childEventListener);
    }

    @Override
    public void refreshFeeds() {
        view.hideProgress();
    }

    public Query getPostsQuery() {
        if (null == postsQuery) {
            this.postsQuery = database.get().getReference().child("posts").orderByPriority();
        }
        return postsQuery;
    }
}
