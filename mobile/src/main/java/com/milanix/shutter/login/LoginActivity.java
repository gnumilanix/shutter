package com.milanix.shutter.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.android.annotations.NonNull;
import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractActivity;
import com.milanix.shutter.databinding.ActivityLoginBinding;
import com.milanix.shutter.home.HomeActivity;

import javax.inject.Inject;

/**
 * Activity containing login
 *
 * @author milan
 */
public class LoginActivity extends AbstractActivity<LoginContract.Presenter, ActivityLoginBinding> implements LoginContract.View {

    @Inject
    InputMethodManager inputMethodManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().with(new LoginModule(this)).inject(this);
        performBinding(R.layout.activity_login);
        binding.setPresenter(presenter);
        binding.setActivity(this);
        binding.setLogin(new Login());

        presenter.subscribe();
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
        new AlertDialog.Builder(this, R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_login_error)
                .setMessage(R.string.message_login_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void setSessionAvailable() {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void setSessionUnavailable() {

    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, getString(R.string.title_logging_in), getString(R.string.missing_password), true);

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
        final View view = getCurrentFocus();

        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
