package com.milanix.shutter.core;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of {@link android.support.v7.widget.RecyclerView.Adapter} that supports
 * basic manipulating of data
 *
 * @param <T> of items
 * @param <H> of the view holder
 * @author milan
 */
public abstract class AbstractRecyclerViewAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    private List<T> items = new ArrayList<>();


    @Override
    public void onBindViewHolder(final H viewHolder, final int position) {
        bind(position, viewHolder, getItem(position));
    }

    /**
     * Binds data to the view
     *
     * @param position   of the holder
     * @param viewHolder to reference to
     * @param item       containing data
     */
    protected abstract void bind(int position, H viewHolder, T item);

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Returns item in the given position
     *
     * @param position of the item
     * @return item if exist, otherwise null
     */
    public T getItem(final int position) {
        return isIndexValid(position) ? items.get(position) : null;
    }


    /**
     * Replaces items with the new ones
     *
     * @param items to replace with
     */
    public void replaceItems(List<T> items) {
        this.items.clear();

        appendItems(items);
    }

    /**
     * Appends new posts
     *
     * @param items to append
     */
    public void appendItems(List<T> items) {
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    /**
     * Returns if the given position is valid
     *
     * @param position to validate
     * @return true if valid
     */
    public boolean isIndexValid(final int position) {
        return position > -1 && position < items.size();
    }

}