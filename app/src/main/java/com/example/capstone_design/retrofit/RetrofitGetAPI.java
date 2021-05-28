package com.example.capstone_design.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitGetAPI {

    @GET("/user")
    Call<Data> getUser(@Header("Authorization") String authorization);
    // 회원 정보

    @GET("/intake")
    Call<RecyclerViewData> getIntake(@Header("Authorization") String authorization, @Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);
    // 사료량

    @GET("/pee")
    Call<RecyclerViewData> getPee(@Header("Authorization") String authorization,@Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);
    // 소변색

    @GET("/poo")
    Call<RecyclerViewData> getPoo(@Header("Authorization") String authorization, @Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);
    // 대변색

    @GET("/weight/fat")
    Call<Data> getFat(@Header("Authorization") String authorization, @Query("dog_status") int status);
    // 체지방
    //GET /intake/recommend
    @GET("intake/recommend")
    Call<Data> getRecommend(@Header("Authorization") String authorization, @Query("date") String date);
}

