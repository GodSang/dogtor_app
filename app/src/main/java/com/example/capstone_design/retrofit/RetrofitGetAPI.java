package com.example.capstone_design.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitGetAPI {

    @GET("/user")
    Call<Data> getUser(@Header("Authorization") String authorization);

    @GET("/intake")
    Call<RecyclerViewData> getIntake(@Header("Authorization") String authorization, @Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);

    @GET("/pee")
    Call<RecyclerViewData> getPee(@Header("Authorization") String authorization,@Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);

    @GET("/poo")
    Call<RecyclerViewData> getPoo(@Header("Authorization") String authorization, @Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date);

}
