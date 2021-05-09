package com.example.capstone_design.fcm;

import com.example.capstone_design.login.Post;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FcmRetrofitAPI {
    @FormUrlEncoded
    @POST("/alarm")
    Call<FcmData> postData(@Header("Authorization") String authorization, @FieldMap HashMap<String, Object> param);
}
