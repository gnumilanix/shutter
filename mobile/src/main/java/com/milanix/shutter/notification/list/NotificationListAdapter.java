package com.milanix.shutter.notification.list;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemNotificationBinding;
import com.milanix.shutter.notification.model.Notification;

import javax.inject.Inject;

/**
 * Adapter containing list of notifications
 *
 * @author milan
 */
public class NotificationListAdapter extends AbstractFirebaseRecyclerAdapter<Notification, NotificationListAdapter.NotificationHolder> {
    private final NotificationListContract.View view;
    private final LayoutInflater inflater;

    @Inject
    public NotificationListAdapter(NotificationListContract.View view, LayoutInflater inflater) {
        this.view = view;
        this.inflater = inflater;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationHolder((ItemNotificationBinding) DataBindingUtil.inflate(inflater, R.layout.item_notification, parent, false));
    }

    @Override
    protected void bind(int position, NotificationHolder viewHolder, Notification item) {
        viewHolder.binding.setNotification(item);
        viewHolder.binding.setView(view);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Notification> getTypeClass() {
        return Notification.class;
    }

    class NotificationHolder extends BindingViewHolder<ItemNotificationBinding> {
        NotificationHolder(ItemNotificationBinding binding) {
            super(binding);
        }
    }
}
