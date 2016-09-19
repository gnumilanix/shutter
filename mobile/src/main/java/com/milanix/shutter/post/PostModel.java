package com.milanix.shutter.post;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.google.firebase.database.Exclude;
import com.milanix.shutter.BR;

import java.util.HashMap;
import java.util.Map;

/**
 * New post data model with support for two way data binding
 *
 * @author milan
 */
public class PostModel extends BaseObservable {
    private Uri imageUri;
    private String title;
    private String description;

    public PostModel() {
    }

    @Bindable
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        notifyPropertyChanged(BR.imageUri);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Exclude
    public Map<String, Object> toMap() {
        final HashMap<String, Object> result = new HashMap<>();
        result.put("image", imageUri.toString());
        result.put("title", title);
        result.put("description", description);

        return result;
    }
}
