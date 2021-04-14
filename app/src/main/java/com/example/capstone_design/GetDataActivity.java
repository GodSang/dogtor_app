package com.example.capstone_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.capstone_design.login.Post;
import com.example.capstone_design.login.RetrofitAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDataActivity extends AppCompatActivity {

    // TEST CODE
//    String user_ID;
//    String user_Password;

    TextView get_data_eat_tv;
    TextView get_data_color_tv;
    String eat;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdata);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        get_data_eat_tv = findViewById(R.id.get_data_eat_tv);
        get_data_color_tv = findViewById(R.id.get_data_color_tv);
        Intent intent_get_info = getIntent();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class); // 인터페이스 생성

        // GET
        retrofitAPI.getData("admin").enqueue(new Callback<List<Post>>() {
            // getData안에 쿼리할 데이터 값을 넣고 인큐함. 인큐는 하나하나 큐에 집어넣는다는 말.
            // 큐는 선입선출임
            // 동기적 통신: .execute(), 비동기적 통신: .enqueue()

            // 성공적으로 get response된 경우
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> data = response.body(); // 우리가 원하는 데이터는 response.body에 있음
                    Log.d("TEST", "GET 성공");

                    eat = data.get(0).getEat();
                    color = data.get(0).getColor();

                    Log.d("RETROFIT", eat);
                    Log.d("RETROFIT", color);

                    get_data_eat_tv.append("eat: " + eat + "g" + "\n");
                    get_data_color_tv.append("color: " + color + "\n");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // POST
        //input.put("id", intent_get_info.getStringExtra("id"));
        //        input.put("password", intent_get_info.getStringExtra("pwd"));
//        HashMap<String, Object> input = new HashMap<>();
//        input.put("id", "admin");
//        input.put("password", "admin");
//        retrofitAPI.postData(input).enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if(response.isSuccessful()) {
//                    Post data = response.body();
//                    Log.d("RETROFIT", "POST 성공");
////
////                    eat = data.getEat();
////                    color = data.getColor();
////
////                    Log.d("RETROFIT", eat);
////                    Log.d("RETROFIT", color);
////
////                    get_data_eat_tv.append("ead: " + eat + "\n");
////                    get_data_color_tv.append("color: " + color + "\n");
//
//                    // GET
//                    retrofitAPI.getData("admin").enqueue(new Callback<List<Post>>() {
//                        // getData안에 쿼리할 데이터 값을 넣고 인큐함. 인큐는 하나하나 큐에 집어넣는다는 말.
//                        // 큐는 선입선출임
//                        // 동기적 통신: .execute(), 비동기적 통신: .enqueue
//
//                        // 성공적으로 get response된 경우
//                        @Override
//                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                            if(response.isSuccessful()){
//                                List<Post> data = response.body(); // 우리가 원하는 데이터는 response.body에 있음
//                                Log.d("TEST", "GET 성공");
//
//                                eat = data.get(0).getEat();
//                                color = data.get(0).getColor();
//
//                                Log.d("RETROFIT", eat);
//                                Log.d("RETROFIT", color);
//
//                                get_data_eat_tv.append("ead: " + eat + "\n");
//                                get_data_color_tv.append("color: " + color + "\n");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Post>> call, Throwable t) {
//                            t.printStackTrace();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }
}