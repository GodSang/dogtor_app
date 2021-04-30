package com.example.capstone_design.initInfo;

import com.example.capstone_design.login.Post;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InitRetofitAPI {

    // get url 변경해야됨
    @GET("/user") // posts 주소임. 앞에 공통된 주소는 다른 곳에서 지정 해줄 것.
    Call<InfoPost> getData(@Header("Authorization") String authorization); // Call 객체를 선언해서 HTTP요청을 웹서버로 보냄
    // JSON 데이터를 <>안에 자료형으로 받겠다는 뜻. 따라서 Post.class를 직접 구현해야 함. id변수에 값을 담아서 userId를 쿼리함.
    //@Query("uid") String uid

    @FormUrlEncoded
    @POST("/user")
    Call<InfoPost> postData(@FieldMap HashMap<String, Object> param); // FieldMap: Field의 형식을 통해 넘겨주는 값이 여러개 일 떄 사용
    // Field형식이란 인자를 form-urlencoded로 보낼 때 사용하는데, form-urlencoded는 key=value&key=value와 같은 형태로 데이터를 전달함.
}
