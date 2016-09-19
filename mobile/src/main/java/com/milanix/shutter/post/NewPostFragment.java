package com.milanix.shutter.post;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNewPostBinding;

import javax.inject.Inject;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment containing new post view
 *
 * @author milan
 */
public class NewPostFragment extends AbstractFragment<NewPostContract.Presenter, FragmentNewPostBinding> implements NewPostContract.View {
    private static final int PICK_POST_IMAGE_REQUEST = 1;
    private final BroadcastReceiver INTENT_RECEIVER = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            handleExtras(intent.getExtras());
        }
    };
    private ProgressDialog progressDialog;

    @Inject
    protected LocalBroadcastManager localBroadcastManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new NewPostModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_new_post, container);
        handleExtras(getArguments());

        return binding.getRoot();
    }

    private void handleExtras(Bundle bundle) {
        try {
            binding.getPost().setImageUri((Uri) bundle.getParcelable(Intent.EXTRA_STREAM));
        } catch (Exception e) {
            Timber.wtf(e, "Some one sent us a bad intent");
        }
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setPresenter(presenter);
        binding.setPost(new PostModel());
        binding.setView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(INTENT_RECEIVER);
    }

    @Override
    public void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(INTENT_RECEIVER, new IntentFilter(Intent.ACTION_SEND));
    }

    @Override
    public void selectPostImage() {
        startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                getString(R.string.title_select_image)), PICK_POST_IMAGE_REQUEST);
    }

    @Override
    public void completePublishPost() {
        Toast.makeText(getActivity(), R.string.message_publish_post_complete, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void handleUploadPostError() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_post_upload_error)
                .setMessage(R.string.message_post_upload_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void handlePublishPostError() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_publish_post_error)
                .setMessage(R.string.message_publish_post_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_uploading_post), getString(R.string.message_uploading_post), true);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_POST_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            binding.getPost().setImageUri(data.getData());
        }
    }

}
