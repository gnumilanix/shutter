package com.milanix.shutter.user.profile.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentProfileBinding;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;
import com.milanix.shutter.user.model.Profile;
import com.milanix.shutter.user.profile.ProfileComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Fragment containing profile
 *
 * @author milan
 */
public class ProfileDetailFragment extends AbstractFragment<ProfileDetailContract.Presenter, FragmentProfileBinding> implements
        ProfileDetailContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected PostListAdapter postListAdapter;
    private OnReadyListener onReadyCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        onReadyCallback.getProfileComponent().with(new ProfileDetailModule(this)).inject(this);
        presenter.subscribe(postListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_profile, container);

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        final Activity activity = getActivity();

        if (activity instanceof ProfileDetailFragment.OnReadyListener) {
            this.onReadyCallback = (ProfileDetailFragment.OnReadyListener) activity;
        } else {
            Timber.d("Caller did not implement OnReadyListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(postListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void setProfile(Profile profile) {
        getActivity().invalidateOptionsMenu();

        if (null != onReadyCallback) {
            onReadyCallback.onReady(this, presenter, profile);
        }
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setAdapter(postListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void openPost(android.view.View view, String postId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view,
                getString(R.string.transition_post_image));

        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtra(PostModule.POST_ID, postId),
                options.toBundle());
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
        startActivity(new Intent(getActivity(), LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void toggleFollow() {
        presenter.toggleFollow();
    }

    @Override
    public void handleToggleFollowError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                "Toggling follow not yet implemented", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.refreshPosts();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.action_logout).setVisible(null != presenter && presenter.isMyProfile());
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

    interface OnReadyListener {
        void onReady(ProfileDetailContract.View view, ProfileDetailContract.Presenter presenter, Profile signUp);

        ProfileComponent getProfileComponent();
    }
}
