package com.milanix.shutter.dependencies.module;

import com.milanix.shutter.user.auth.AuthorizationInterceptor;
import com.milanix.shutter.user.auth.OAuthenticator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module providing network related dependencies
 *
 * @author milan
 */
@Module
public class NetworkModule {
    private static final String BASE_API_URL = "https://shutter.getsandbox.com";

    @Singleton
    @Provides
    public OkHttpClient provideHttpClient(OAuthenticator authenticator,
                                          HttpLoggingInterceptor loggingInterceptor,
                                          AuthorizationInterceptor authorizationInterceptor) {
        return new OkHttpClient.Builder().authenticator(authenticator)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authorizationInterceptor).build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient).build();
    }
}
