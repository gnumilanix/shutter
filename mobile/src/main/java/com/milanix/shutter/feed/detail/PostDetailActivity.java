package com.milanix.shutter.feed.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractBindingActivity;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.ActivityPostDetailBinding;
import com.milanix.shutter.feed.PostComponent;
import com.milanix.shutter.feed.PostModule;

/**
 * Activity containing a single feed
 *
 * @author milan
 */
public class PostDetailActivity extends AbstractBindingActivity<ActivityPostDetailBinding> implements IComponentProvider<PostComponent> {
    public static final String TAG_FRAGMENT_POST_DETAIL = "_fragment_post_detail";
    private PostComponent postComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performBinding(R.layout.activity_post_detail);
        setToolbar(binding.toolbar, true);
        createPostComponent(getIntent().getExtras().getString(PostModule.POST_ID));

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new PostDetailFragment(),
                    TAG_FRAGMENT_POST_DETAIL).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }

    private void createPostComponent(String postId) {
        postComponent = getUserComponent().with(new PostModule(postId));
    }
}
