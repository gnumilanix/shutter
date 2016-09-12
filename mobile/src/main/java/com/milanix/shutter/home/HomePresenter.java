package com.milanix.shutter.home;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.core.AbstractPresenter;

/**
 * Home presenter
 *
 * @author milan
 */
public class HomePresenter extends AbstractPresenter<HomeContract.View> implements HomeContract.Presenter, ValueEventListener {
    private final FirebaseUser user;
    private final Query unreadQuery;

    public HomePresenter(HomeContract.View view, FirebaseUser user,FirebaseDatabase database) {
        super(view);
        this.user = user;
        unreadQuery=database.getReference().child("activities").child(user.getUid()).orderByChild("read").equalTo(false);
    }

    @Override
    public void subscribe() {
        super.subscribe();
        unreadQuery.addValueEventListener(this);
        view.setUser(user);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        unreadQuery.removeEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(isActive()){
            if(!dataSnapshot.hasChildren())
                view.removeUnread();
            else
                view.showUnread((int)dataSnapshot.getChildrenCount());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
