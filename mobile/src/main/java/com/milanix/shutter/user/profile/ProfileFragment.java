package com.milanix.shutter.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentProfileBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.FeedDetailActivity;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.login.LoginActivity;
import com.milanix.shutter.user.model.User;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment containing profile
 *
 * @author milan
 */
public class ProfileFragment extends AbstractFragment<ProfileContract.Presenter, FragmentProfileBinding> implements
        ProfileContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    PostListAdapter postListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getUserComponent().with(new ProfileModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_profile, container);
        presenter.getProfile();

        return binding.getRoot();
    }

    @Override
    public void showProfile(User user) {
        binding.setUser(user);
    }

    @Override
    public void showPosts(List<Feed> posts) {
        postListAdapter.replaceItems(posts);
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setAdapter(postListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void openPost(long postId) {
        startActivity(new Intent(getActivity(), FeedDetailActivity.class).putExtra(FeedModule.FEED_ID, postId));
    }

    @Override
    public void handleProfileRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                R.string.error_refresh_profile, Snackbar.LENGTH_SHORT).show();
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
    public void logoutComplete() {
        getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onRefresh() {
        presenter.refreshProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                presenter.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
