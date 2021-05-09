package com.example.capstone_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.capstone_design.fcm.FcmData;
import com.example.capstone_design.fcm.FcmRetrofitAPI;
import com.example.capstone_design.initInfo.InfoPost;
import com.example.capstone_design.initInfo.InitRetofitAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private String TAG_HOME_FRAGMENT = "Fragment_main_home";
    private String TAG_GUIDE_FRAGMENT = "Fragment_guide";
    private String TAG_RECORD_FRAGMENT = "Fragment_record";
    private String TAG_SETTING_FRAGMENT = "Fragment_setting";

    FragmentMainHome fragmentMainHome = new FragmentMainHome();
    FragmentGuide fragmentGuide = new FragmentGuide();
    FragmentRecord fragmentRecord = new FragmentRecord();
    FragmentSetting fragmentSetting = new FragmentSetting();


    InitRetofitAPI initRetofitAPI;
    FcmRetrofitAPI fcmRetrofitAPI;

    String uid;
    String fcm_token;
    String name, gender, type;
    int birth, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);


        //uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                .build();
        initRetofitAPI = retrofit.create(InitRetofitAPI.class); // 인터페이스 생성
        fcmRetrofitAPI = retrofit.create(FcmRetrofitAPI.class);


        // 파이버베이스로부터 FCM 토큰 받아오고 서버로 받아온 토큰 POST 하는 함수 ----
        getToken();
        //---------------------------------------------------------------------------

        // GET 요청
        initRetofitAPI.getData(uid).enqueue(new Callback<InfoPost>() {
            @Override
            public void onResponse(Call<InfoPost> call, Response<InfoPost> response) {
                if(response.isSuccessful()){
                    InfoPost data = response.body();
                    // 홈 프래그먼트로 보낼 데이터들

                    Log.d("hyeals_bundle", data.getDog_name());
                    name = data.getDog_name();
                    birth = data.getDog_birth();
                    gender = data.getDog_gender();
                    type = data.getDog_type();
                    weight =  data.getDog_weight();
                }

                FragmentView(TAG_HOME_FRAGMENT, fragmentMainHome);
            }

            @Override
            public void onFailure(Call<InfoPost> call, Throwable t) {
                    t.printStackTrace();
            }
        });


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
            if(tag == TAG_HOME_FRAGMENT){
                Bundle home_bundle = new Bundle(5);
                home_bundle.putString("dog_name",name);
                home_bundle.putInt("dog_birth", birth);
                home_bundle.putString("dog_gender", gender);
                home_bundle.putString("dog_type", type);
                home_bundle.putInt("dog_weight", weight);
                fragmentMainHome.setArguments(home_bundle);

                Log.d("hyeals_bundle_name", home_bundle.getString("dog_name"));
            }

            if(tag == TAG_RECORD_FRAGMENT){
                Bundle bundle_record = new Bundle(1);
                bundle_record.putString("uid", uid);
                fragmentRecord.setArguments(bundle_record);
            }

            if(tag == TAG_SETTING_FRAGMENT){
                Bundle bundle = new Bundle(1);
                bundle.putString("uid", uid);
                fragmentSetting.setArguments(bundle);
            }
            transaction.add(R.id.fragment, fragment, tag);
        }

        Fragment main_home = manager.findFragmentByTag(TAG_HOME_FRAGMENT);
        Fragment guide = manager.findFragmentByTag(TAG_GUIDE_FRAGMENT);
        Fragment record = manager.findFragmentByTag(TAG_RECORD_FRAGMENT);
        Fragment setting = manager.findFragmentByTag(TAG_SETTING_FRAGMENT);

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
                Log.d("fcm_uid", uid);

                fcmRetrofitAPI.postData(uid, input).enqueue(new Callback<FcmData>() {
                    @Override
                    public void onResponse(Call<FcmData> call, Response<FcmData> response) {
                        Log.d("fcm_post", "fcm 토큰 서버에 전송 완료: " + fcm_token);
                    }

                    @Override
                    public void onFailure(Call<FcmData> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
            return;
    }
}