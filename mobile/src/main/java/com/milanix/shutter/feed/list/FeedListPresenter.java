package com.milanix.shutter.feed.list;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements FeedListContract.Presenter {
    private final Query query;

    @Inject
    public FeedListPresenter(FeedListContract.View view, FirebaseDatabase database) {
        super(view);
        this.query = database.getReference().child("posts").orderByChild("createTime");
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        super.subscribe();
        query.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        super.unsubscribe();
        query.removeEventListener(childEventListener);
    }

    @Override
    public void refreshFeeds() {
        view.hideProgress();
    }
}
