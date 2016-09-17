package com.milanix.shutter.core.specification;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.milanix.shutter.core.AbstractRecyclerAdapter;

/**
 * Abstract implementation of {@link RecyclerView.Adapter} that supports fire base {@link ChildEventListener}
 *
 * @param <T> of items
 * @param <H> of the view holder
 * @author milan
 */
public abstract class AbstractFirebaseRecyclerAdapter<T extends IFirebaseModel, H extends RecyclerView.ViewHolder>
        extends AbstractRecyclerAdapter<T, H> implements ChildEventListener {

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        final T item = getValue(dataSnapshot);
        items.add(item);
        notifyItemInserted(items.indexOf(item));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        final T item = getValue(dataSnapshot);
        final int index = items.indexOf(item);
        items.set(index, item);
        notifyItemChanged(index);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        final T item = getValue(dataSnapshot);
        final int index = items.indexOf(item);
        items.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        final T item = getValue(dataSnapshot);
        final int index = items.indexOf(item);
        items.remove(index);
        int newIndex = s == null ? 0 : (getIndexOf(s) + 1);
        items.add(newIndex, item);
        notifyItemMoved(index, newIndex);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private int getIndexOf(String key) {
        int index = 0;

        for (T item : items) {
            if (item.key().equals(key)) {
                return index;
            } else {
                index++;
            }
        }

        throw new IllegalArgumentException("Key not found");
    }

    private T getValue(DataSnapshot snapshot) {
        return snapshot.getValue(getTypeClass());
    }

    protected abstract Class<T> getTypeClass();

}