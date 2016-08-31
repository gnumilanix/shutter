package com.milanix.shutter.auth.resetpassword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.milanix.shutter.BR;

/**
 * Sign up data model with support for two way data binding
 *
 * @author milan
 */
public class RequestPassword extends BaseObservable {
    private String email;

    public RequestPassword() {
    }

    public RequestPassword(String email) {
        this.email = email;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
}
