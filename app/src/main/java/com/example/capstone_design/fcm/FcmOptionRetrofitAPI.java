package com.example.capstone_design.fcm;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FcmOptionRetrofitAPI {
    @FormUrlEncoded
    @POST("/alarm/option")
    Call<FcmData> postData(@Header("Authorization") String authorization, @FieldMap HashMap<String, Object> param);
}
