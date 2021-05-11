package com.example.capstone_design.fcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.capstone_design.MainActivity;
import com.example.capstone_design.R;
import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FcmSettingActivity extends AppCompatActivity {

    ImageView back_btn;
    Switch fcm_on_off_switch;

    String uid;
    RetrofitClient retrofitClient = new RetrofitClient();

    SharedPreferences saveShared = getSharedPreferences("fcm_option", MODE_PRIVATE);
    SharedPreferences.Editor sharedEditor = saveShared.edit();

    SharedPreferences loadShared = getSharedPreferences("fcm_option", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_setting);

        back_btn = findViewById(R.id.back_btn);
        fcm_on_off_switch = findViewById(R.id.fcm_on_off_switch);

        // uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        //switch 내부 DB(SharedPreferences에 option키 값에 value있으면 그걸로 option, 없으면 on이 디폴트
        fcm_on_off_switch.setChecked(loadShared.getBoolean("option", true));
        Log.d("FcmSettingActivity", "내부 DB 옵션 값: " + loadShared.getBoolean("option", false));

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
                    sharedEditor.putBoolean("option", true);
                    sharedEditor.commit();
                    Log.d("FcmSettingActivity", "내부 DB 옵션 값: " + loadShared.getBoolean("option", false));
                    Log.d("FcmSettingActivity", "옵션 체크 상태: OFF");
                }else{
                    sendFcmOptionToServer(0);
                    sharedEditor.putBoolean("option", false);
                    sharedEditor.commit();
                    Log.d("FcmSettingActivity", "내부 DB 옵션 값: " + loadShared.getBoolean("option", true));
                    Log.d("FcmSettingActivity", "옵션 체크 상태: OFF");
                }
            }
        });
    }

    private void sendFcmOptionToServer(int OptionFlag){
        HashMap<String, Object> input = new HashMap<>();

        input.put("optionFlag", OptionFlag);

        retrofitClient.retrofitPostAPI.postAlarmOption(uid, input).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("FcmSettingActivity", "데이터 보내기 성공! uid: "+ uid + "OptionFlag: " + OptionFlag);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
