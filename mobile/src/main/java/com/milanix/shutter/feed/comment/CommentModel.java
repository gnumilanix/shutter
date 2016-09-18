package com.milanix.shutter.feed.comment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.milanix.shutter.BR;

/**
 * Comment model data model with support for two way data binding
 *
 * @author milan
 */
public class CommentModel extends BaseObservable {
    private String comment;

    public CommentModel() {
    }

    public CommentModel(String comment) {
        this.comment = comment;
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

}
