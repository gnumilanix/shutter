package com.milanix.shutter.post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNewPostBinding;

import static android.app.Activity.RESULT_OK;

//// TODO: 30/8/2016 comment
public class NewPostFragment extends AbstractFragment<NewPostContract.Presenter, FragmentNewPostBinding> implements NewPostContract.View {
    private static final int PICK_POST_IMAGE_REQUEST = 1;
    private final Observable.OnPropertyChangedCallback propertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            getActivity().invalidateOptionsMenu();
        }
    };
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new NewPostModule(this)).inject(this);
        setHasOptionsMenu(true);
        performBinding(inflater, R.layout.fragment_new_post, container);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setPresenter(presenter);
        binding.setPost(new NewPost());
        binding.setView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.getPost().addOnPropertyChangedCallback(propertyChangedCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.getPost().removeOnPropertyChangedCallback(propertyChangedCallback);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_new_post, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        setPostState(binding.getPost(), menu.findItem(R.id.action_post));
    }

    private void setPostState(NewPost post, MenuItem item) {
        final Uri imageUri = post.getImageUri();
        item.setVisible(null != imageUri && !TextUtils.isEmpty(imageUri.toString()) &&
                !TextUtils.isEmpty(post.getTitle()) && !TextUtils.isEmpty(post.getDescription()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_post:
                presenter.publishPost(binding.getPost());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void selectPostImage() {
        startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                getString(R.string.title_select_image)), PICK_POST_IMAGE_REQUEST);
    }

    @Override
    public void completePublishPost() {
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
            getActivity().invalidateOptionsMenu();
        }
    }

}
