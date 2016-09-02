package com.milanix.shutter.feed.list;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemFeedBinding;
import com.milanix.shutter.feed.model.Post;

import javax.inject.Inject;

/**
 * Adapter containing list of feed
 *
 * @author milan
 */
public class FeedListAdapter extends AbstractFirebaseRecyclerAdapter<Post, FeedListAdapter.FeedHolder> {
    private FeedListContract.View feedListView;
    private final LayoutInflater inflater;

    @Inject
    public FeedListAdapter(FeedListContract.View feedListView, LayoutInflater inflater) {
        this.feedListView = feedListView;
        this.inflater = inflater;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedHolder((ItemFeedBinding) DataBindingUtil.inflate(inflater, R.layout.item_feed, parent, false));
    }

    @Override
    protected void bind(int position, FeedHolder viewHolder, Post item) {
        viewHolder.binding.setPost(item);
        viewHolder.binding.setView(feedListView);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Post> getTypeClass() {
        return Post.class;
    }

    class FeedHolder extends BindingViewHolder<ItemFeedBinding> {
        FeedHolder(ItemFeedBinding binding) {
            super(binding);
        }
    }
}
