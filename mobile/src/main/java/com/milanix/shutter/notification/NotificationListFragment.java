package com.milanix.shutter.notification;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNotificationListBinding;
import com.milanix.shutter.notification.model.Notification;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class NotificationListFragment extends AbstractFragment<NotificationListContract.Presenter, FragmentNotificationListBinding> implements
        NotificationListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected NotificationListAdapter notificationListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserComponent().with(new NotificationListModule(this)).inject(this);
        presenter.subscribe(notificationListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_notification_list, container);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setAdapter(notificationListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(notificationListAdapter);
    }

    @Override
    public void showProgress() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void openNotification(Notification notification) {
        presenter.markRead(notification);
    }

    @Override
    public void onRefresh() {
        presenter.refreshNotification();
    }
}
