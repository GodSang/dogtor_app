package com.example.capstone_design.record;

import com.example.capstone_design.initInfo.InfoPost;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecordIntakeRetrofitAPI {

    @GET("/intake") // posts 주소임. 앞에 공통된 주소는 다른 곳에서 지정 해줄 것.
    Call<GetData> getData(@Header("Authorization") String authorization, @Query("page") int page,
                          @Query("limit") int limit, @Query("date") String date); // Call 객체를 선언해서 HTTP요청을 웹서버로 보냄

}
