package com.milanix.shutter.feed.comment;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemCommentBinding;
import com.milanix.shutter.feed.model.Comment;

import javax.inject.Inject;

/**
 * Adapter containing list of comments
 *
 * @author milan
 */
public class CommentListAdapter extends AbstractFirebaseRecyclerAdapter<Comment, CommentListAdapter.CommentHolder> {
    private final CommentListContract.View view;
    private final LayoutInflater inflater;

    @Inject
    public CommentListAdapter(CommentListContract.View view, LayoutInflater inflater) {
        this.view = view;
        this.inflater = inflater;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder((ItemCommentBinding) DataBindingUtil.inflate(inflater, R.layout.item_comment, parent, false));
    }

    @Override
    protected void bind(int position, CommentHolder viewHolder, Comment item) {
        viewHolder.binding.setComment(item);
        viewHolder.binding.setView(view);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Comment> getTypeClass() {
        return Comment.class;
    }

    class CommentHolder extends BindingViewHolder<ItemCommentBinding> {
        CommentHolder(ItemCommentBinding binding) {
            super(binding);
        }
    }
}
