package com.milanix.shutter.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentLoginBinding;
import com.milanix.shutter.databinding.FragmentSignupBinding;
import com.milanix.shutter.home.HomeActivity;

import javax.inject.Inject;

//// TODO: 30/8/2016 comment
public class SignUpFragment extends AbstractFragment<SignUpContract.Presenter, FragmentSignupBinding> implements SignUpContract.View {
    @Inject
    InputMethodManager inputMethodManager;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new SignUpModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_signup, container);
        binding.setPresenter(presenter);
        binding.setFragment(this);
        binding.setSignup(new SignUp());

        return binding.getRoot();
    }


    @Override
    public void handleExistingUser() {

    }

    @Override
    public void handleSignUpFailure() {

    }

    @Override
    public void handleUploadAvatarFailure() {

    }

    @Override
    public void completeSignUp() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
}
