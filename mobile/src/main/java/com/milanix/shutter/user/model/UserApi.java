package com.milanix.shutter.user.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit interface providing user related API
 *
 * @author milan
 */
public interface UserApi {

    @GET("api/v1/user/me")
    Call<User> getMe();

    @GET("api/v1/user/{userId}")
    Call<User> getUser(@Path("userId") String userId);

}
