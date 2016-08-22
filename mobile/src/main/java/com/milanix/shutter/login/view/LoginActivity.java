package com.milanix.shutter.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractActivity;
import com.milanix.shutter.databinding.ActivityLoginBinding;
import com.milanix.shutter.feed.list.view.FeedListActivity;
import com.milanix.shutter.login.LoginContract;
import com.milanix.shutter.login.LoginModule;
import com.milanix.shutter.login.model.Login;

/**
 * Activity containing login
 *
 * @author milan
 */
public class LoginActivity extends AbstractActivity<LoginContract.Presenter, ActivityLoginBinding> implements LoginContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().with(new LoginModule(this)).inject(this);
        performBinding(R.layout.activity_login);
        binding.setPresenter(presenter);
        binding.setLogin(new Login());

        presenter.subscribe();
    }

    @Override
    public void handleInvalidPassword() {

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
        startActivity(new Intent(this, FeedListActivity.class));
    }

    @Override
    public void setSessionUnavailable() {

    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.vgLoginForm.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
        binding.vgLoginForm.setVisibility(View.VISIBLE);
    }
}
