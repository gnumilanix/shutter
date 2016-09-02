package com.milanix.shutter.feed.list;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements FeedListContract.Presenter {
    private final FirebaseDatabase database;

    @Inject
    public FeedListPresenter(FeedListContract.View view, FirebaseDatabase database) {
        super(view);
        this.database = database;
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        super.subscribe();
        database.getReference().child("posts").addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        super.unsubscribe();
        database.getReference().child("posts").removeEventListener(childEventListener);
    }

    @Override
    public void refreshFeeds() {
    }
}
