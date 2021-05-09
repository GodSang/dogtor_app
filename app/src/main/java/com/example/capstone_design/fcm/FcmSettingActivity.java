package com.example.capstone_design.fcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.capstone_design.MainActivity;
import com.example.capstone_design.R;
import com.example.capstone_design.login.LoginRetrofitAPI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FcmSettingActivity extends AppCompatActivity {

    ImageView back_btn;
    Switch fcm_on_off_switch;

    String uid;
    FcmOptionRetrofitAPI fcmOptionRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_setting);

        back_btn = findViewById(R.id.back_btn);
        fcm_on_off_switch = findViewById(R.id.fcm_on_off_switch);

        // uid 가져오기기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                .build();
        fcmOptionRetrofitAPI = retrofit.create(FcmOptionRetrofitAPI.class); // 인터페이스 생성

        //switch 디폴트 설정: 알림 켜진 상태
        fcm_on_off_switch.setChecked(true);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        fcm_on_off_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    sendFcmOptionToServer(1);
                    Log.d("FcmSettingActivity", "옵션 체크 상태: ON");
                }else{
                    sendFcmOptionToServer(0);
                    Log.d("FcmSettingActivity", "옵션 체크 상태: OFF");
                }
            }
        });
    }

    private void sendFcmOptionToServer(int OptionFlag){
        HashMap<String, Object> input = new HashMap<>();

        input.put("optionFlag", OptionFlag);

        fcmOptionRetrofitAPI.postData(uid, input).enqueue(new Callback<FcmData>() {
            @Override
            public void onResponse(Call<FcmData> call, Response<FcmData> response) {
                Log.d("FcmSettingActivity", "데이터 보내기 성공! uid: "+ uid + "OptionFlag: " + OptionFlag);
            }

            @Override
            public void onFailure(Call<FcmData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
