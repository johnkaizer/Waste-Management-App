package com.galaxiescoders.wastetracker.Service;


import com.galaxiescoders.wastetracker.Interceptor.AccessToken;
import com.galaxiescoders.wastetracker.MpesaDaraja.STKPush;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface STKPushService {
    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush);

    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken();
}
