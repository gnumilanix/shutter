package com.milanix.shutter.home;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.core.AbstractPresenter;

import dagger.Lazy;

/**
 * Home presenter
 *
 * @author milan
 */
public class HomePresenter extends AbstractPresenter<HomeContract.View> implements HomeContract.Presenter, ValueEventListener {
    private final Lazy<FirebaseUser> user;
    private final Lazy<FirebaseDatabase> database;
    private Query unreadQuery;

    public HomePresenter(HomeContract.View view, Lazy<FirebaseUser> user, Lazy<FirebaseDatabase> database) {
        super(view);
        this.user = user;
        this.database = database;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        getUnreadQuery().addValueEventListener(this);
        view.setUser(user.get());
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        getUnreadQuery().removeEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (isActive()) {
            if (!dataSnapshot.hasChildren())
                view.removeUnread();
            else
                view.showUnread((int) dataSnapshot.getChildrenCount());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public Query getUnreadQuery() {
        if (null == unreadQuery) {
            unreadQuery = database.get().getReference().child("activities").child(user.get().getUid()).orderByChild("read").equalTo(false);
        }

        return unreadQuery;
    }
}
