package com.milanix.shutter.auth.login;

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
import com.milanix.shutter.auth.resetpassword.RequestPasswordActivity;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentLoginBinding;
import com.milanix.shutter.home.HomeActivity;

import javax.inject.Inject;

//// TODO: 30/8/2016 comment
public class LoginFragment extends AbstractFragment<LoginContract.Presenter, FragmentLoginBinding> implements LoginContract.View {
    @Inject
    InputMethodManager inputMethodManager;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new LoginModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_login, container);
        binding.setPresenter(presenter);
        binding.setFragment(this);
        binding.setLogin(new Login());

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        setBoldUnderlineSpan(binding.tvRequestPassword, getString(R.string.action_signin_forget_password),
                getString(R.string.action_signin_forget_password_highlight));
    }

    @Override
    public void handleInvalidUsername() {
        binding.tlEmail.setErrorEnabled(true);
        binding.tlEmail.setError(getString(R.string.missing_email));
    }

    @Override
    public void handleInvalidPassword() {
        binding.tlPassword.setErrorEnabled(true);
        binding.tlPassword.setError(getString(R.string.missing_password));
    }

    @Override
    public void handleInvalidLogin() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_login_error)
                .setMessage(R.string.message_login_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void setSessionAvailable() {
        getActivity().finish();
        startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void setSessionUnavailable() {

    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_logging_in), getString(R.string.message_logging_in), true);

        binding.tlEmail.setErrorEnabled(false);
        binding.tlPassword.setErrorEnabled(false);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    public boolean onEditorAction(@NonNull final TextView view, final int actionId, @Nullable final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard();
            presenter.login(binding.getLogin());
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

    public void requestPassword() {
        startActivity(new Intent(getActivity(), RequestPasswordActivity.class));
    }

    private void setBoldUnderlineSpan(TextView view, String fullText, String textToStyle) {
        final int beginIndex = fullText.indexOf(textToStyle);
        final int endIndex = beginIndex + textToStyle.length();

        final SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        view.setText(spannableString);
    }
}
