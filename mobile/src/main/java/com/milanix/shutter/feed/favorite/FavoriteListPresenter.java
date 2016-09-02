package com.milanix.shutter.feed.favorite;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;


/**
 * Feeds presenter
 *
 * @author milan
 */
public class FavoriteListPresenter extends AbstractPresenter<FavoriteListContract.View> implements FavoriteListContract.Presenter {
    private final FirebaseUser user;
    private final FirebaseDatabase database;

    @Inject
    public FavoriteListPresenter(FavoriteListContract.View view, FirebaseUser user, FirebaseDatabase database) {
        super(view);
        this.user = user;
        this.database = database;
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        database.getReference().child("posts").orderByChild("favoriters/" + user.getUid() + "/").equalTo(true).addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        database.getReference().child("posts").orderByChild("favoriters/" + user.getUid() + "/").equalTo(true).addChildEventListener(childEventListener);
    }

    @Override
    public void refreshFavorites() {

    }
}
