package com.milanix.shutter.feed.detail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentPostDetailBinding;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.user.profile.ProfileModule;
import com.milanix.shutter.user.profile.detail.ProfileDetailActivity;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class PostDetailFragment extends AbstractFragment<PostDetailContract.Presenter, FragmentPostDetailBinding> implements PostDetailContract.View {
    private static final int RC_SHARE_POST = 100;
    private ProgressDialog progressDialog;

    @Inject
    protected FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getApp().getPostComponent().with(new PostDetailModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_post_detail, container);
        setStatusColor(getActivity(), android.R.color.black);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setPresenter(presenter);
        binding.setUser(user);
        binding.setView(this);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusColor(Activity activity, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(activity, color));
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.action_share).setVisible(binding.getPost() != null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_post_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                presenter.share(binding.getPost());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showPost(Post post) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(post.getTitle());
        getActivity().invalidateOptionsMenu();
        binding.setPost(post);
    }

    @Override
    public void handlePostRetrieveError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                R.string.error_refresh_feed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void share(String title, String message, Uri imageUri) {
        startActivityForResult(new AppInviteInvitation.IntentBuilder(title)
                .setMessage(message)
                .setCustomImage(imageUri)
                .setCallToActionText(getString(R.string.action_view))
                .build(), RC_SHARE_POST);
    }

    @Override
    public void handlePostShareError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                R.string.error_download_post, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_preparing_image),
                getString(R.string.message_preparing_image), true);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void completeMarkFavorite() {

    }

    @Override
    public void handleMarkFavoriteError() {
        binding.ivFavorites.setChecked(true);
    }

    @Override
    public void openProfile(String profileId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), binding.ivAvatar,
                getString(R.string.transition_profile_image));

        startActivity(new Intent(getActivity(), ProfileDetailActivity.class).putExtra(ProfileModule.PROFILE_ID, profileId),
                options.toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SHARE_POST) {
            if (resultCode == Activity.RESULT_OK) {
                //// TODO: 3/9/2016 map invitation ids to user
            }
        }
    }
}
