package com.app.dcsg.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Srikanth on 1/02/18
 */

public class ServiceCall {

    private static final String LOG_TAG = "ServiceCall";

    public static NetworkService getNetworkService() {

        Gson gson = new GsonBuilder().setLenient().create();

        RetrofitRequestInterceptor responseInterceptor = new RetrofitRequestInterceptor();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(responseInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(NetworkService.class);
    }

    private static class RetrofitRequestInterceptor implements Interceptor {

        public RetrofitRequestInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();
            //Ensure Content type is JSON
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }
}


