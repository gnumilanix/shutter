package com.milanix.shutter.auth.signup;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.milanix.shutter.BR;

/**
 * Sign up data model with support for two way data binding
 *
 * @author milan
 */
public class SignUp extends BaseObservable {
    private String username;
    private String email;
    private String password;
    private Uri avatar;

    public SignUp() {
    }

    public SignUp(String username, String email, String password, Uri avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public Uri getAvatar() {
        return avatar;
    }

    public void setAvatar(Uri avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }
}
