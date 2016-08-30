package com.milanix.shutter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.milanix.shutter.login.AuthActivity;

/**
 * Splash activity
 *
 * @author milan
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
}
