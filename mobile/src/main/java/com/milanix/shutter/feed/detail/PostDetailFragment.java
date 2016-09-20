package com.milanix.shutter.feed.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.FragmentPostDetailBinding;
import com.milanix.shutter.feed.PostComponent;
import com.milanix.shutter.feed.comment.CommentListActivity;
import com.milanix.shutter.feed.comment.CommentListModule;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileModule;

import org.parceler.Parcels;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Fragment containing post detail
 *
 * @author milan
 */
public class PostDetailFragment extends AbstractFragment<PostDetailContract.Presenter, FragmentPostDetailBinding> implements
        PostDetailContract.View, RequestListener<String, GlideDrawable> {
    private static final int RC_SHARE_POST = 100;
    private final AtomicBoolean isViewMarked = new AtomicBoolean(false);
    private ProgressDialog progressDialog;
    private IComponentProvider<PostComponent> componentProvider;

    @Inject
    protected FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        componentProvider.getComponent().with(new PostDetailModule(this)).inject(this);
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
        binding.setPostImageListener(this);
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
    public void onAttach(Context context) {
        super.onAttach(context);

        final Activity activity = getActivity();

        if (activity instanceof IComponentProvider) {
            this.componentProvider = (IComponentProvider) activity;
        } else {
            Timber.d("Caller did not implement OnReadyListener");
        }
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
                .setMessage(truncate(message, 99))
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

    }

    @Override
    public void openProfile(String profileId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                binding.ivAvatar, getString(R.string.transition_profile_image));

        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra(ProfileModule.PROFILE_ID, profileId),
                options.toBundle());
    }

    @Override
    public void openComment(Post post) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                binding.ivImage, getString(R.string.transition_post_image));

        startActivity(new Intent(getActivity(), CommentListActivity.class).putExtra(CommentListModule.POST,
                Parcels.wrap(post)), options.toBundle());
    }

    @Override
    public void toggleViewState() {
        toggleToolbarVisibility();
        toggleActionsVisibility();
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

    public static String truncate(String text, int maxLength) {
        return (text.length() > maxLength) ? text.substring(0, maxLength - 3) + "..." : text;
    }

    private void toggleToolbarVisibility() {
        final View toolbar = getActivity().findViewById(R.id.toolbar);

        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setAlpha(1.0f);
            toolbar.animate()
                    .translationY(-toolbar.getHeight())
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            toolbar.setVisibility(View.GONE);
                        }
                    });
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setAlpha(0.0f);
            toolbar.setTranslationY(-toolbar.getHeight());
            toolbar.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            toolbar.setTranslationY(0);
                            toolbar.setAlpha(1.0f);
                        }
                    });
        }
    }

    private void toggleActionsVisibility() {
        if (binding.clActions.getVisibility() == View.VISIBLE) {
            binding.clActions.setAlpha(1.0f);
            binding.clActions.animate()
                    .translationY(binding.clActions.getHeight())
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            binding.clActions.setVisibility(View.GONE);
                        }
                    });
        } else {
            binding.clActions.setVisibility(View.VISIBLE);
            binding.clActions.setAlpha(0.0f);
            binding.clActions.setTranslationY(binding.clActions.getHeight());
            binding.clActions.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            binding.clActions.setTranslationY(0);
                            binding.clActions.setAlpha(1.0f);
                        }
                    });
        }
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                   boolean isFromMemoryCache, boolean isFirstResource) {
        if (!isViewMarked.get() && isActive()) {
            isViewMarked.set(true);
            presenter.markViewed();
        }
        return false;
    }
}
