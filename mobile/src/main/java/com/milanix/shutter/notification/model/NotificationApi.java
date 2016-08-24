package com.milanix.shutter.notification.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit interface providing notification related API
 *
 * @author milan
 */
public interface NotificationApi {
    @FormUrlEncoded
    @POST("api/v1/notifications")
    Call<Notification> putNotification(Notification notification);

    @GET("api/v1/notifications")
    Call<List<Notification>> getNotifications();

    @GET("api/v1/notifications/{notificationId}")
    Call<List<Notification>> getNotifications(@Path("notificationId") long notificationId);
}
