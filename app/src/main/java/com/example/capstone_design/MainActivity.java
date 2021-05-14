package com.example.capstone_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private String TAG_HOME_FRAGMENT = "Fragment_main_home";
    private String TAG_GUIDE_FRAGMENT = "Fragment_guide";
    private String TAG_RECORD_FRAGMENT = "Fragment_record";
    private String TAG_SETTING_FRAGMENT = "Fragment_setting";
    private String TAG_WEIGHT_FRAGMENT = "Fragment_weight";

    private String token;

    FragmentMainHome fragmentMainHome = new FragmentMainHome();
    FragmentGuide fragmentGuide = new FragmentGuide();
    FragmentRecord fragmentRecord = new FragmentRecord();
    FragmentSetting fragmentSetting = new FragmentSetting();
    FragmentWeight fragmentWeight =  new FragmentWeight();

    RetrofitClient retrofitClient = new RetrofitClient();

    String fcm_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loadShared = getSharedPreferences("DB", MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);

        token = loadShared.getString("token", "");

        // 파이버베이스로부터 FCM 토큰 받아오고 서버로 받아온 토큰 POST 하는 함수 ----
        getToken();
        //---------------------------------------------------------------------------

        FragmentView(TAG_HOME_FRAGMENT, fragmentMainHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_home:
                        FragmentView(TAG_HOME_FRAGMENT, fragmentMainHome);
                        break;
                    case R.id.guide:
                        FragmentView(TAG_GUIDE_FRAGMENT, fragmentGuide);
                        break;
                    case R.id.record:
                        FragmentView(TAG_RECORD_FRAGMENT, fragmentRecord);
                        break;
                    case R.id.setting:
                        FragmentView(TAG_SETTING_FRAGMENT, fragmentSetting);
                        break;
                    case R.id.weight:
                        FragmentView(TAG_WEIGHT_FRAGMENT, fragmentWeight);
                }

                return true;
            }
        });
    }

    private void FragmentView(String tag, Fragment fragment){
        //FragmentTransaction를 이용해 프래그먼트를 사용.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(manager.findFragmentByTag(tag)==null){

            transaction.add(R.id.fragment, fragment, tag);
        }

        Fragment main_home = manager.findFragmentByTag(TAG_HOME_FRAGMENT);
        Fragment guide = manager.findFragmentByTag(TAG_GUIDE_FRAGMENT);
        Fragment record = manager.findFragmentByTag(TAG_RECORD_FRAGMENT);
        Fragment setting = manager.findFragmentByTag(TAG_SETTING_FRAGMENT);
        Fragment weight = manager.findFragmentByTag(TAG_WEIGHT_FRAGMENT);

        // Hide all Fragment
        if(main_home != null){
            transaction.hide(main_home);
        }
        if(guide != null){
            transaction.hide(guide);
        }
        if(record != null){
            transaction.hide(record);
        }
        if(setting != null){
            transaction.hide(setting);
        }
        if(weight != null){
            transaction.hide(weight);
        }

        //Show current Fragment
        if(tag == TAG_HOME_FRAGMENT){
            if(main_home!=null){
                transaction.show(main_home);
            }
        }
        if(tag == TAG_GUIDE_FRAGMENT){
            if(guide!=null){
                transaction.show(guide);
            }
        }
        if(tag == TAG_RECORD_FRAGMENT){
            if(record!=null){
                transaction.show(record);
            }
        }
        if(tag == TAG_SETTING_FRAGMENT){
            if(setting!=null){
                transaction.show(setting);
            }
        }
        if(tag == TAG_WEIGHT_FRAGMENT){
            if(weight!=null){
                transaction.show(weight);
            }
        }

        transaction.commitAllowingStateLoss();

    }

    // 현재 사용중인 FCM 토큰 확인
    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    Log.d("main_token", "인스턴스 아이디 가져오지 못함", task.getException());
                    return;
                }
                // 인스턴스 아이디 값이 있다면
                Log.d("main_token", "토큰 값: " + task.getResult().getToken());
                fcm_token = task.getResult().getToken();

                // FCM 토큰 서버로 POST -------------------------------------------------------
                HashMap<String, Object> input = new HashMap<>();
                input.put("key", fcm_token);
                Log.d("fcm_token", fcm_token);

                retrofitClient.retrofitPostAPI.postAlarm(token, input).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        Log.d("fcm_post", "fcm 토큰 서버에 전송 완료: " + fcm_token);
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
            return;
    }

}