package com.milanix.shutter.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentAuthBinding;

/**
 * Created by milan on 30/8/2016.
 */

public class AuthFragment extends AbstractFragment<AuthContract.Presenter, FragmentAuthBinding> implements AuthContract.View {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new AuthModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_auth, container);
        binding.setView(this);
        binding.setPresenter(presenter);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        setLoginUnderlineHint();
    }

    private void setLoginUnderlineHint() {
        final String fullText = getString(R.string.action_signin_existing);
        final String textToUnderline = getString(R.string.action_signin_existing_login);
        final int beginIndex = fullText.indexOf(textToUnderline);
        final int endIndex = beginIndex + textToUnderline.length();

        final SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        binding.tvLogin.setText(spannableString);
    }

    @Override
    public void login() {

    }

    @Override
    public void signUp() {

    }

    @Override
    public void updateProfile() {

    }

    @Override
    public void completeLogin() {

    }

    @Override
    public void handleLoginError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
