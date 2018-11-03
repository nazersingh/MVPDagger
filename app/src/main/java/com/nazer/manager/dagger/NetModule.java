package com.nazer.manager.dagger;

import android.annotation.SuppressLint;
import android.app.Application;

import com.nazer.BuildConfig;
import com.nazer.manager.application.MyApplication;
import com.nazer.manager.client.AllApi;
import com.nazer.manager.client.ApiInterface;
import com.nazer.manager.client.AppConfig;
import com.nazer.manager.db.PreferenceHandler;
import com.nazer.util.PrintLog;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {


    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    public AllApi getAllApi() {
        return new AllApi();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        client.cache(cache);
        if (BuildConfig.DEBUG)
            client.addInterceptor(new StethoInterceptor());
        client.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        client.addInterceptor(new Interceptor() {
            @SuppressLint("LongLogTag")
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String mLoginToken = new PreferenceHandler().readString(MyApplication.getInstance().getApplicationContext(), PreferenceHandler.FCM_TOKEN, "");
                if (mLoginToken != null && mLoginToken.trim().length() > 0) {
                    PrintLog.e("NetModule---login token--", "--" + mLoginToken);
                    Request request = original.newBuilder()
                            .header("x-logintoken", mLoginToken)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
                return chain.proceed(original);
            }
        });
        return client.build();
    }

    @Provides
    @Singleton
    @Named("Breezy")
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(AppConfig.getBaseURL())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("payment")
    Retrofit provideMangoRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(MyApplication.PAYMET_GATEWAY_BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiInterface getAPIService(@Named("Breezy") Retrofit retro) {
        return retro.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    @Named("payment")
    ApiInterface getPaymentAPIService(@Named("mango") Retrofit retro) {
        return retro.create(ApiInterface.class);
    }


}
