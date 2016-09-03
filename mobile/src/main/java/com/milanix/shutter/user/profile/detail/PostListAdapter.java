package com.milanix.shutter.user.profile.detail;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemPostBinding;
import com.milanix.shutter.feed.model.Post;

import javax.inject.Inject;

/**
 * Adapter containing list of posts
 *
 * @author milan
 */
public class PostListAdapter extends AbstractFirebaseRecyclerAdapter<Post, PostListAdapter.NotificationHolder> {
    private final ProfileDetailContract.View feedListView;
    private final FirebaseUser user;
    private final LayoutInflater inflater;

    @Inject
    public PostListAdapter(ProfileDetailContract.View feedListView, FirebaseUser user, LayoutInflater inflater) {
        this.feedListView = feedListView;
        this.user = user;
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
        viewHolder.binding.setUser(user);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Post> getTypeClass() {
        return Post.class;
    }

    class NotificationHolder extends BindingViewHolder<ItemPostBinding> {
        NotificationHolder(ItemPostBinding binding) {
            super(binding);
        }
    }
}
