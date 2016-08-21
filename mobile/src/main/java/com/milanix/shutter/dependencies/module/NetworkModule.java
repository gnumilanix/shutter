package com.milanix.shutter.dependencies.module;

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
    private static final String BASE_API_URL = "https://api.shutter.com";

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

    @Singleton
    @Provides
    public OkHttpClient provideHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }
}
