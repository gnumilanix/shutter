package com.milanix.shutter.feed.favorite;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractRecyclerAdapter;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.databinding.ItemFavoriteBinding;
import com.milanix.shutter.feed.model.Feed;

import javax.inject.Inject;

/**
 * Adapter containing list of feed
 *
 * @author milan
 */
public class FavoriteListAdapter extends AbstractRecyclerAdapter<Feed, FavoriteListAdapter.FeedHolder> {
    private FavoriteListContract.View feedListView;
    private final LayoutInflater inflater;

    @Inject
    public FavoriteListAdapter(FavoriteListContract.View feedListView, LayoutInflater inflater) {
        this.feedListView = feedListView;
        this.inflater = inflater;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedHolder((ItemFavoriteBinding) DataBindingUtil.inflate(inflater, R.layout.item_favorite, parent, false));
    }

    @Override
    protected void bind(int position, FeedHolder viewHolder, Feed item) {
        viewHolder.binding.setFeed(item);
        viewHolder.binding.setView(feedListView);
        viewHolder.binding.executePendingBindings();
    }

    class FeedHolder extends BindingViewHolder<ItemFavoriteBinding> {
        FeedHolder(ItemFavoriteBinding binding) {
            super(binding);
        }
    }
}
