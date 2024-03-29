package com.galaxiescoders.wastetracker.Interceptor;


import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {
    public AccessTokenInterceptor() {

    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Replace these values with the actual ones from your gradle.properties file
        String consumerKey = "C8BpXBMBEIuFjTPmdZwblOYU0NCSZ7pp";
        String consumerSecret = "TdFu97Q069wZoZ8Q";

        String keys = new StringBuilder().append(consumerKey).append(":").append(consumerSecret).toString();

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic " + Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP))
                .build();
        return chain.proceed(request);
    }
}

