package com.milanix.shutter.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milanix.shutter.R;
import com.milanix.shutter.databinding.ActivityLoginBinding;
import com.milanix.shutter.feed.list.view.FeedListActivity;
import com.milanix.shutter.login.LoginContract;
import com.milanix.shutter.login.LoginModule;
import com.milanix.shutter.login.model.Login;
import com.milanix.shutter.core.AbstractActivity;

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
        binding.setActivity(this);
        binding.setPresenter(presenter);
        binding.setLogin(new Login());
    }

    @Override
    public void handleInvalidPassword() {

    }

    @Override
    public void handleInvalidLogin() {

    }

    @Override
    public void setSessionAvailable() {
        startActivity(new Intent(this, FeedListActivity.class));
    }

    @Override
    public void setSessionUnavailable() {

    }
}
