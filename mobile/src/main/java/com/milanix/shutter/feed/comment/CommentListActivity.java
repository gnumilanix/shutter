package com.milanix.shutter.feed.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.databinding.ActivityCommentBinding;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileModule;

import org.parceler.Parcels;

import javax.inject.Inject;

// TODO: 5/9/2016 refactor profile detail fragment to only contain posts and define view and presenter contract for this activity instead.

/**
 * Activity containing a user profile
 *
 * @author milan
 */
public class CommentListActivity extends AbstractBindingActivity<ActivityCommentBinding> implements CommentListContract.View {

    @Inject
    protected CommentListContract.Presenter presenter;
    @Inject
    protected CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserComponent().with(new CommentListModule(getPost(getIntent().getExtras()), this)).inject(this);
        performBinding(R.layout.activity_comment);
        setToolbar(binding.toolbar, true);
        presenter.subscribe(adapter);
    }

    private Post getPost(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(CommentListModule.POST));
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setPresenter(presenter);
        binding.setComment(new CommentModel());
        binding.setLayoutManager(new LinearLayoutManager(this));
        binding.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(adapter);
    }

    @Override
    public void showPost(Post post) {
        getSupportActionBar().setTitle(post.getTitle());
        getSupportActionBar().setSubtitle(getResources().getQuantityString(R.plurals.comments,
                adapter.getItemCount(), adapter.getItemCount()));
        binding.setPost(post);
    }

    @Override
    public void handlePostRetrieveError() {
        Snackbar.make(binding.root, R.string.error_retrieve_post, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleSendCommentError() {
        Snackbar.make(binding.root, R.string.error_send_comment, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void openProfile(View view, String authorId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                getString(R.string.transition_profile_image));

        startActivity(new Intent(this, ProfileActivity.class).putExtra(ProfileModule.PROFILE_ID, authorId),
                options.toBundle());
    }
}
