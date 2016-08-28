package com.milanix.shutter.user.auth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * /**
 * Retrofit interface providing auth related API
 *
 * @author milan
 */
public interface AuthApi {

    @FormUrlEncoded
    @POST("api/v1/oauth2/token")
    Call<Authorization> authorize(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("grant_type") String grantType,
                                  @Field("client_id") String clientId,
                                  @Field("client_secret") String clientSecret);

    @FormUrlEncoded
    @POST("api/v1/oauth2/token")
    Call<Authorization> getToken(@Field("grant_type") String grantType,
                                 @Field("client_id") String clientId,
                                 @Field("client_secret") String clientSecret,
                                 @Field("code") String authorizationCode);

    @FormUrlEncoded
    @POST("api/v1/oauth2/token")
    Call<Authorization> refreshToken(@Field("grant_type") String grantType,
                                     @Field("client_id") String clientId,
                                     @Field("client_secret") String clientSecret,
                                     @Field("refresh_token") String refreshToken);

    @FormUrlEncoded
    @POST("api/v1/oauth2/revoke")
    Call<ResponseBody> logout(@Field("access_token") String accessToken);

    @FormUrlEncoded
    @POST("api/v1/users/signup")
    Call<Authorization> signUp(@Field("username") String username,
                               @Field("password") String password,
                               @Field("email") String email,
                               @Field("first_name") String firstName,
                               @Field("last_name") String lastName);
}
