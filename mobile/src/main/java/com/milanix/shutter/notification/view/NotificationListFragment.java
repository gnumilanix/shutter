package com.milanix.shutter.notification.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNotificationListBinding;
import com.milanix.shutter.notification.NotificationListContract;
import com.milanix.shutter.notification.NotificationListModule;
import com.milanix.shutter.notification.model.Notification;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class NotificationListFragment extends AbstractFragment<NotificationListContract.Presenter, FragmentNotificationListBinding> implements
        NotificationListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NotificationListAdapter feedListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new NotificationListModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_notification_list, container);

        presenter.getNotifications();

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setAdapter(feedListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void showNotifications(List<Notification> notifications) {
        binding.getAdapter().appendItems(notifications);
    }

    @Override
    public void handleNotificationsRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "Error refreshing notifications", Snackbar.LENGTH_SHORT);
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
        presenter.refreshNotifications();
    }
}
