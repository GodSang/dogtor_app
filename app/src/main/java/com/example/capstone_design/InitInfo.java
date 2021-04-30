package com.example.capstone_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstone_design.initInfo.InfoPost;
import com.example.capstone_design.initInfo.InitRetofitAPI;
import com.example.capstone_design.login.LoginRetrofitAPI;
import com.example.capstone_design.login.Post;

import java.util.HashMap;
import java.util.function.ToDoubleBiFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitInfo extends AppCompatActivity {

    String uid;
    Intent intent_main;

    Button dog_service_start;
    // TODO: 아직 이미지 선택 처리부분은 안함 (추후  다이얼로그로 할 것)
    EditText dog_name_ed;
    EditText dog_birth_ed;
    EditText dog_gender_ed;
    EditText dog_type_ed;
    EditText dog_weight_ed;

    private InitRetofitAPI initRetofitAPI;

    //String saveSharedName= "dog_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_info);

        // 내부 DB 생성
        //SharedPreferences saveShared = getSharedPreferences(saveSharedName, MODE_PRIVATE);
        //SharedPreferences.Editor sharedEditor = saveShared.edit();

        //uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        dog_service_start = findViewById(R.id.dog_service_start);
        dog_name_ed = findViewById(R.id.dog_name);
        dog_birth_ed = findViewById(R.id.dog_age); // dog_birth // Integer
        dog_gender_ed = findViewById(R.id.dog_gender); // dog_gender // String
        dog_type_ed = findViewById(R.id.dog_kind); // dog_type // String
        dog_weight_ed = findViewById(R.id.dog_weight); // dog_weight // integer

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                //.baseUrl("http://172.30.1.7")
                .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                //.client(okHttpClient)
                .build();
        initRetofitAPI = retrofit.create(InitRetofitAPI.class); // 인터페이스 생성

        dog_service_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                HashMap<String, Object> input = new HashMap<>();

                input.put("uid", uid);
                input.put("dog_name", dog_name_ed.getText().toString());
                input.put("dog_birth", Integer.parseInt(dog_birth_ed.getText().toString()));
                input.put("dog_gender", dog_gender_ed.getText().toString());
                input.put("dog_type", dog_type_ed.getText().toString());
                input.put("dog_weight", Integer.parseInt(dog_weight_ed.getText().toString()));

                initRetofitAPI.postData(input).enqueue(new Callback<InfoPost>() {
                    @Override
                    public void onResponse(Call<InfoPost> call, Response<InfoPost> response) {
                        if(response.isSuccessful()){
                            InfoPost data = response.body();

                            // TODO: response 처리
                            // TODO: response 데이터 내부 DB에 넣기
                            
                            intent_main = new Intent(getApplicationContext(), MainActivity.class);
                            intent_main.putExtra("uid", uid);

                            // 내부 DB에 강아지 정보 저장
                            //sharedEditor.putString("dog_name", data.getDog_name());
                            //sharedEditor.putInt("dog_birth", data.getDog_birth());
                            //sharedEditor.putString("dog_gender", data.getDog_gender());
                            //sharedEditor.putString("dog_type", data.getDog_type());
                            //sharedEditor.putInt("dog_weight", data.getDog_weight());

                            //sharedEditor.commit();

                            startActivity(intent_main);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<InfoPost> call, Throwable t) {
                        t.printStackTrace();
                        t.getMessage();
                    }
                });
            }
        });

    }
}