package com.milanix.shutter.notification;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.milanix.shutter.R;
import com.milanix.shutter.feed.model.Author;
import com.milanix.shutter.notification.model.Notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationGenerator {
    private final FirebaseUser user;
    private final FirebaseDatabase database;

    public NotificationGenerator(FirebaseUser user, FirebaseDatabase database) {
        this.user = user;
        this.database = database;
    }

    public Map<String, Object> generate(@Notification.Type String type) {
        switch (type) {
            case Notification.Type.UNFOLLOW:
                return new HashMap<>();
            case Notification.Type.UNFAVORITE:
                return new HashMap<>();
            default:
                throw new UnsupportedOperationException("type must be un-follow or un-favorite");
        }
    }

    public Map<String, Object> generate(@Notification.Type String type, String userId, Notification.Post post) {
        if (type.equals(Notification.Type.UNFOLLOW) && type.equals(Notification.Type.UNFAVORITE)) {
            throw new UnsupportedOperationException("type must not be un-follow or un-favorite");
        }

        final String activityId = database.getReference().child("activities").push().getKey();

        final Map<String, Object> author = new Author(user.getUid(), user.getDisplayName(),
                user.getPhotoUrl().toString()).toMap();

        final Map<String, Object> activityValues = new HashMap<>();
        activityValues.put("id", activityId);
        activityValues.put("read", false);
        activityValues.put("time", ServerValue.TIMESTAMP);
        activityValues.put("type", type);
        activityValues.put("author", author);

        if (null != post) {
            final HashMap<String, Object> postValues = new HashMap<>();
            postValues.put("id", post.getId());
            postValues.put("image", post.getImage());

            activityValues.put("post", postValues);
        }

        final Map<String, Object> update = new HashMap<>();
        update.put("/activities/" + userId + "/" + activityId, activityValues);

        return update;
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
                case Notification.Type.COMMENT:
                    view.setText(view.getContext().getString(R.string.message_notification_comment, notification.getAuthor().getName()));
                    break;
                case Notification.Type.FOLLOW:
                    view.setText(view.getContext().getString(R.string.message_notification_following, notification.getAuthor().getName()));
                    break;
                case Notification.Type.FAVORITE:
                    view.setText(view.getContext().getString(R.string.message_notification_favorite, notification.getAuthor().getName()));
                    break;
                case Notification.Type.NEWS:
                    view.setText(notification.getMessage());
                    break;
                case Notification.Type.UNFOLLOW:
                    break;
                case Notification.Type.UNFAVORITE:
                    break;
            }
        }
    }

}
