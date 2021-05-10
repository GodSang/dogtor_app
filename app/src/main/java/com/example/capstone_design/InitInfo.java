package com.example.capstone_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;

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

    RetrofitClient retrofitClient = new RetrofitClient();

    //String saveSharedName= "dog_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_info);

        //uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        dog_service_start = findViewById(R.id.dog_service_start);
        dog_name_ed = findViewById(R.id.dog_name);
        dog_birth_ed = findViewById(R.id.dog_age); // dog_birth // Integer
        dog_gender_ed = findViewById(R.id.dog_gender); // dog_gender // String
        dog_type_ed = findViewById(R.id.dog_kind); // dog_type // String
        dog_weight_ed = findViewById(R.id.dog_weight); // dog_weight // integer

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

                retrofitClient.retrofitPostAPI.postUser(input).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(response.isSuccessful()){
                            Data data = response.body();

                            intent_main = new Intent(getApplicationContext(), MainActivity.class);
                            intent_main.putExtra("uid", uid);

                            startActivity(intent_main);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

    }
}