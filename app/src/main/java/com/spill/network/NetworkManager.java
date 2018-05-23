package com.spill.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String API_HOST = "https://api.unsplash.com/";
    private static final String CLIENT_ID = "8aefe4ed957d998578c2d9c6f0ce48e33d4c6db9c2a279b61b6437bfb66fc28d";
    private static final String PARAMETER_CLIENT_ID = "client_id";
    private static final NetworkManager INSTANCE = new NetworkManager();
    private final UnsplashApi unsplashApi;

    public static UnsplashApi getUnsplashApi() {
        return INSTANCE.unsplashApi;
    }

    private NetworkManager() {
        final Interceptor interceptor = provideRequestInterceptor();
        final OkHttpClient okHttpClient = provideOkHttp(interceptor);
        final Retrofit retrofit = provideRetrofit(okHttpClient);
        unsplashApi = provideApi(retrofit);
    }

    private UnsplashApi provideApi(Retrofit retrofit) {
        return retrofit.create(UnsplashApi.class);
    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private OkHttpClient provideOkHttp(Interceptor interceptor) {
        HttpLoggingInterceptor.Logger logger = message -> Log.d("OkHttp", message);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger)
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .build();

    }

    private Interceptor provideRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request originalRequest = chain.request();
                final HttpUrl newUrl = originalRequest.url().newBuilder()
                        .addQueryParameter(PARAMETER_CLIENT_ID, CLIENT_ID)
                        .build();
                final Request newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }
}
