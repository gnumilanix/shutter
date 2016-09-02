package com.milanix.shutter.auth.signup;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.milanix.shutter.R;
import com.milanix.shutter.auth.LandingActivity;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentSignupBinding;
import com.milanix.shutter.home.HomeActivity;

import javax.inject.Inject;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

//// TODO: 30/8/2016 comment
public class SignUpFragment extends AbstractFragment<SignUpContract.Presenter, FragmentSignupBinding> implements SignUpContract.View, PermissionListener {
    private static final int PICK_AVATAR_REQUEST = 1;
    @Inject
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog progressDialog;

    private DialogOnDeniedPermissionListener dialogOnDeniedStoragePermissionListener;
    private OnReadyListener onReadyCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new SignUpModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_signup, container);
        createPermissionListeners();

        Dexter.continuePendingRequestIfPossible(dialogOnDeniedStoragePermissionListener);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setPresenter(presenter);
        binding.setFragment(this);
        binding.setSignup(new SignUp());
        binding.setView(this);

        setBoldUnderlineSpan(binding.cbAgreement, getString(R.string.action_signin_accept_terms),
                getString(R.string.action_signin_accept_terms_highlight));
        setBoldUnderlineSpan(binding.tvLogin, getString(R.string.action_signin_existing),
                getString(R.string.action_signin_existing_highlight));

        if (null != onReadyCallback) {
            onReadyCallback.onReady(this, binding.getSignup());
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

    private void setBoldUnderlineSpan(TextView view, String fullText, String textToStyle) {
        final int beginIndex = fullText.indexOf(textToStyle);
        final int endIndex = beginIndex + textToStyle.length();

        final SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        view.setText(spannableString);
    }

    @Override
    public void handleExistingUser() {

    }

    @Override
    public void handleSignUpFailure() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_signup_error)
                .setMessage(R.string.message_signup_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void handleUploadAvatarFailure() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_upload_error)
                .setMessage(R.string.message_avatar_upload_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void completeSignUp() {
        startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void selectAvatar() {
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
    public void openAgreement() {

    }

    @Override
    public void openLogin() {
        startActivity(new Intent(getActivity(), LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_signing_up), getString(R.string.message_signing_up), true);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    public boolean onEditorAction(@NonNull final TextView view, final int actionId, @Nullable final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard();
            presenter.signUp(binding.getSignup());
            return true;
        }

        return false;
    }

    private void hideKeyboard() {
        final View view = getActivity().getCurrentFocus();

        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void createPermissionListeners() {
        dialogOnDeniedStoragePermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                        .withTitle(R.string.storage_permission_request_title)
                        .withMessage(R.string.storage_permission_request_rationale_avatar)
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
                .setMessage(R.string.storage_permission_request_rationale_avatar)
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

        if (requestCode == PICK_AVATAR_REQUEST && resultCode == RESULT_OK && data != null) {
            binding.getSignup().setAvatar(data.getData());
        }
    }

    private void launchImagePicker() {
        startActivityForResult(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                getString(R.string.title_select_avatar)), PICK_AVATAR_REQUEST);
    }

    interface OnReadyListener {
        void onReady(SignUpContract.View view, SignUp signUp);
    }
}
