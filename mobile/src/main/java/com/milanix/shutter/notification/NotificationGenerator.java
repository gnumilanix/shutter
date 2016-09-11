package com.milanix.shutter.notification;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.milanix.shutter.R;
import com.milanix.shutter.core.specification.IFirebaseModel;
import com.milanix.shutter.feed.model.Author;
import com.milanix.shutter.notification.model.Notification;
import com.milanix.shutter.notification.model.Notification.Type;

import java.util.HashMap;
import java.util.Map;

import static com.milanix.shutter.notification.model.Notification.Type.COMMENT;
import static com.milanix.shutter.notification.model.Notification.Type.FAVORITE;
import static com.milanix.shutter.notification.model.Notification.Type.FOLLOW;
import static com.milanix.shutter.notification.model.Notification.Type.NEWS;
import static com.milanix.shutter.notification.model.Notification.Type.UNFAVORITE;
import static com.milanix.shutter.notification.model.Notification.Type.UNFOLLOW;

public class NotificationGenerator {
    private final FirebaseUser user;
    private final FirebaseDatabase database;

    public NotificationGenerator(FirebaseUser user, FirebaseDatabase database) {
        this.user = user;
        this.database = database;
    }

    public Map<String, Object> generate(@Type String type, String userId, Notification.Post post) {
        final Map<String, Object> update = new HashMap<>();
        final Map<String, Object> activityValues = new HashMap<>();
        final String activityId = generateId(type, user.getUid(), post);

        switch (type) {
            case COMMENT:
            case FAVORITE:
            case FOLLOW:
            case NEWS: {
                activityValues.put("id", activityId);
                activityValues.put("read", false);
                activityValues.put("time", ServerValue.TIMESTAMP);
                activityValues.put("type", type);
                activityValues.put("author", new Author(user.getUid(), user.getDisplayName(),
                        user.getPhotoUrl().toString()).toMap());
                activityValues.put("post", getPostValues(type, post));
                update.put("/activities/" + userId + "/" + activityId, activityValues);
                break;
            }
            case UNFAVORITE:
            case UNFOLLOW:
                update.put("/activities/" + userId + "/" + activityId, null);
        }

        return update;
    }

    private HashMap<String, Object> getPostValues(@Type String type, Notification.Post post) {
        final HashMap<String, Object> postValues = new HashMap<>();

        switch (type) {
            case COMMENT:
            case FAVORITE:
            case UNFAVORITE: {
                postValues.put("id", post.getId());
                postValues.put("image", post.getImage());
            }
        }

        return postValues;
    }

    private String generateId(@Type String type, String userId, IFirebaseModel firebaseModel) {
        switch (type) {
            case COMMENT:
                return firebaseModel.key() + "_" + userId + "_" + COMMENT;
            case FAVORITE:
            case UNFAVORITE:
                return firebaseModel.key() + "_" + userId + "_" + FAVORITE;
            case FOLLOW:
            case UNFOLLOW:
                return userId + "_" + FOLLOW;
            default:
                return database.getReference().child("activities").push().getKey();
        }

    }

    /**
     * Generates notification from given notification
     *
     * @param view to load image to
     */
    @BindingAdapter(value = {"notification"})
    public static void setNotification(TextView view, Notification notification) {
        if (null != notification) {
            switch (notification.getType()) {
                case COMMENT:
                    view.setText(view.getContext().getString(R.string.message_notification_comment, notification.getAuthor().getName()));
                    break;
                case FOLLOW:
                    view.setText(view.getContext().getString(R.string.message_notification_following, notification.getAuthor().getName()));
                    break;
                case FAVORITE:
                    view.setText(view.getContext().getString(R.string.message_notification_favorite, notification.getAuthor().getName()));
                    break;
                case NEWS:
                    view.setText(notification.getMessage());
                    break;
            }
        }
    }

}
