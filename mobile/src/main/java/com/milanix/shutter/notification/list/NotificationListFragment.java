package com.milanix.shutter.notification.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNotificationListBinding;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;
import com.milanix.shutter.notification.model.Notification;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Inject;

/**
 * Fragment containing notification list view
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

        binding.setLayoutManager(getLayoutManager());
        binding.setAdapter(notificationListAdapter);
        binding.setRefreshListener(this);
    }

    private LinearLayoutManager getLayoutManager() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        return layoutManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(notificationListAdapter);
    }

    @Override
    public void openPost(View view, Notification notification) {
        presenter.markRead(notification.getId());

        if (null != notification.getPost()) {
            final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    view.findViewById(R.id.ivPostImage), getString(R.string.transition_post_image));

            startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtra(PostModule.POST_ID,
                    notification.getPost().getId()), options.toBundle());
        }
    }

    @Override
    public void openProfile(View view, String notificationId, String authorId) {
        presenter.markRead(notificationId);

        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view,
                getString(R.string.transition_profile_image));

        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra(ProfileModule.PROFILE_ID, authorId),
                options.toBundle());
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
    public void onRefresh() {
        presenter.refreshNotification();
    }
}
