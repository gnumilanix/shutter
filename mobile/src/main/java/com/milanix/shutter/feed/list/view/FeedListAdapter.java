package com.milanix.shutter.feed.list.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.databinding.ItemFeedBinding;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.specs.AbstractRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Adapter containing list of feed
 *
 * @author milan
 */
public class FeedListAdapter extends AbstractRecyclerViewAdapter<Feed, FeedListAdapter.FeedHolder> {
    private LayoutInflater inflater;

    @Inject
    public FeedListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedHolder((ItemFeedBinding) DataBindingUtil.inflate(inflater, R.layout.item_feed, parent, false));
    }

    @Override
    protected void bind(int position, FeedHolder viewHolder, Feed item) {
        viewHolder.binding.setFeed(item);
    }

    public class FeedHolder extends RecyclerView.ViewHolder {
        private final ItemFeedBinding binding;

        public FeedHolder(ItemFeedBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
