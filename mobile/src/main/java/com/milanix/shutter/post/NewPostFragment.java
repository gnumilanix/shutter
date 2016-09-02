package com.milanix.shutter.post;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentNewPostBinding;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

//// TODO: 30/8/2016 comment
public class NewPostFragment extends AbstractFragment<NewPostContract.Presenter, FragmentNewPostBinding> implements NewPostContract.View, PermissionListener {
    private static final int PICK_POST_IMAGE_REQUEST = 1;
    private final Observable.OnPropertyChangedCallback propertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            getActivity().invalidateOptionsMenu();
        }
    };
    private DialogOnDeniedPermissionListener dialogOnDeniedStoragePermissionListener;
    private ProgressDialog progressDialog;
    private OnReadyListener onReadyCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new NewPostModule(this)).inject(this);
        setHasOptionsMenu(true);
        performBinding(inflater, R.layout.fragment_new_post, container);
        createPermissionListeners();

        Dexter.continuePendingRequestIfPossible(dialogOnDeniedStoragePermissionListener);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setPresenter(presenter);
        binding.setPost(new NewPost());
        binding.setView(this);

        if (null != onReadyCallback) {
            onReadyCallback.onReady(this, binding.getPost());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        final Activity activity = getActivity();

        if (activity instanceof OnReadyListener) {
            this.onReadyCallback = (OnReadyListener) activity;
        } else {
            Timber.d("Caller did not implement OnReadyListener");
        }
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!Dexter.isRequestOngoing())
                Dexter.checkPermission(dialogOnDeniedStoragePermissionListener, Manifest.permission.READ_EXTERNAL_STORAGE);
            else
                Dexter.continuePendingRequestIfPossible(dialogOnDeniedStoragePermissionListener);
        } else {
            launchImagePicker();
        }
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

    private void createPermissionListeners() {
        dialogOnDeniedStoragePermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                        .withTitle(R.string.storage_permission_request_title)
                        .withMessage(R.string.storage_permission_request_rationale_post)
                        .withButtonText(android.R.string.ok)
                        .build();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        launchImagePicker();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        Timber.i("%s denied", response.getPermissionName());
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        showPermissionRationale(token);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.storage_permission_request_title)
                .setMessage(R.string.storage_permission_request_rationale_post)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_POST_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            binding.getPost().setImageUri(data.getData());
            getActivity().invalidateOptionsMenu();
        }
    }

    private void launchImagePicker() {
        startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                getString(R.string.title_select_image)), PICK_POST_IMAGE_REQUEST);
    }

    interface OnReadyListener {
        void onReady(NewPostContract.View view, NewPost post);
    }
}
