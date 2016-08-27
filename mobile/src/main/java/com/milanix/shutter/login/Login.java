package com.milanix.shutter.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.milanix.shutter.BR;

/**
 * Login data model with support for two way data binding
 *
 * @author milan
 */
public class Login extends BaseObservable {
    private String username;
    private String password;

    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
