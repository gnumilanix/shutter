package com.milanix.shutter.feed.favorite;

import com.google.firebase.auth.FirebaseUser;
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
public class FavoriteListPresenter extends AbstractPresenter<FavoriteListContract.View> implements FavoriteListContract.Presenter {
    private final FirebaseUser user;
    private final Query query;

    @Inject
    public FavoriteListPresenter(FavoriteListContract.View view, FirebaseUser user, FirebaseDatabase database) {
        super(view);
        this.user = user;
        this.query = database.getReference().child("posts").orderByChild("favoriters/" + user.getUid() + "/").equalTo(true);
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        query.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        query.addChildEventListener(childEventListener);
    }

    @Override
    public void refreshFavorites() {

    }
}
