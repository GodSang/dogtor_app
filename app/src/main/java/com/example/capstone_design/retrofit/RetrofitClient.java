package com.example.capstone_design.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://13.209.18.94:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RetrofitGetAPI retrofitGetAPI = retrofit.create(RetrofitGetAPI.class);
    public RetrofitPostAPI retrofitPostAPI = retrofit.create(RetrofitPostAPI.class);
}

