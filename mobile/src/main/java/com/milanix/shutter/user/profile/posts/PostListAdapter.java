package com.milanix.shutter.user.profile.posts;

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
 * Adapter containing posts
 *
 * @author milan
 */
public class PostListAdapter extends AbstractFirebaseRecyclerAdapter<Post, PostListAdapter.NotificationHolder> {
    private final PostListContract.View view;
    private final FirebaseUser user;
    private final LayoutInflater inflater;

    @Inject
    public PostListAdapter(PostListContract.View view, FirebaseUser user, LayoutInflater inflater) {
        this.view = view;
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
        viewHolder.binding.setView(view);
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
