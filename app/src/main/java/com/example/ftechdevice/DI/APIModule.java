package com.example.ftechdevice.DI;

import com.example.ftechdevice.API_Service.ProductAPI_Service;
import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.API_Service.VNPay_Service;
import com.example.ftechdevice.API_Service.YoutubeAPI_Service;
import com.example.ftechdevice.AppConfig.BaseAPI.BaseAPI;

import javax.inject.Singleton;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import com.google.firebase.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import dagger.Module;
import dagger.Provides;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class APIModule {


    @Provides
    @Singleton
    @Named("youtube")
    public static String provideYoutubeBaseUrl() {
        return BaseAPI.BASE_YOUTUBE_URL;
    }

    @Provides
    @Singleton
    @Named("user")
    public static String provideUserBaseUrl() {
        return BaseAPI.BASE_API;
    }

    @Provides
    @Singleton
    @Named("base")
    public static String provideBaseUrl() {
        return BaseAPI.BASE_API_PRM;
    }

    @Provides
    @Singleton
    public static int provideConnectionTimeout() {
        return (int) BaseAPI.NETWORK_TIMEOUT;
    }

    @Provides
    @Singleton
    public static Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl url = chain.request()
                        .url()
                        .newBuilder()
                        .build();

                Request request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            }
        };
        builder.addInterceptor(requestInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    public static YoutubeAPI_Service provideYoutubeAPI(@Named("youtube") String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(YoutubeAPI_Service.class);
    }
    @Provides
    @Singleton
    public static UserAPI_Service provideUserAPI(@Named("base") String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UserAPI_Service.class);

    }
    @Provides
    @Singleton
    public static VNPay_Service provideVNPayService(@Named("base") String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(VNPay_Service.class);

    }

    @Provides
    @Singleton
    public static ProductAPI_Service provideProductAPI(@Named("base") String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ProductAPI_Service.class);

    }
}
