package com.milanix.shutter.user.profile.detail;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractRecyclerAdapter;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.databinding.ItemPostBinding;
import com.milanix.shutter.feed.model.Post;

import javax.inject.Inject;

/**
 * Adapter containing list of posts
 *
 * @author milan
 */
public class PostListAdapter extends AbstractRecyclerAdapter<Post, PostListAdapter.NotificationHolder> {
    private ProfileDetailContract.View feedListView;
    private final LayoutInflater inflater;

    @Inject
    public PostListAdapter(ProfileDetailContract.View feedListView, LayoutInflater inflater) {
        this.feedListView = feedListView;
        this.inflater = inflater;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationHolder((ItemPostBinding) DataBindingUtil.inflate(inflater, R.layout.item_post, parent, false));
    }

    @Override
    protected void bind(int position, NotificationHolder viewHolder, Post item) {
        viewHolder.binding.setPost(item);
        viewHolder.binding.setView(feedListView);
        viewHolder.binding.executePendingBindings();
    }

    class NotificationHolder extends BindingViewHolder<ItemPostBinding> {
        NotificationHolder(ItemPostBinding binding) {
            super(binding);
        }
    }
}
