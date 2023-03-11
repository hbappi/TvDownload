package com.sttech.tvdownload.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String BASE_URL = "https://mctv.banttechenergies.com/";
    public static Retrofit retrofit = null;
    public static API api;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(120, TimeUnit.SECONDS);
            httpClient.writeTimeout(120, TimeUnit.SECONDS);
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging);  // <-- this is the important line!
            OkHttpClient httpClientt = new OkHttpClient();
            httpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();

                    return chain.proceed(request);

                }
            });
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(GsonConverterFactory.create().asLenient())
//                    .addConverterFactory(GsonConverterFactory.create().asLenient())
                    //    addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static API get() {
        api = getClient().create(API.class);
        return api;
    }

}
