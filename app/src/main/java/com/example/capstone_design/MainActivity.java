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

import com.example.capstone_design.initInfo.InfoPost;
import com.example.capstone_design.initInfo.InitRetofitAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    String uid;
    String name, gender, type;
    int birth, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FragmentMainHome()).commit();

        //uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
//            Request newRequest = chain.request().newBuilder().addHeader("Authorization", "testuid22").build();
//            return chain.proceed(newRequest);
//        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                //.client(client) // 헤더에 UID 값 보내는 부분
                .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                .build();
        initRetofitAPI = retrofit.create(InitRetofitAPI.class); // 인터페이스 생성

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
                        //:TODO home 눌렀을 때 home 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(TAG_HOME_FRAGMENT, fragmentMainHome);
                        break;
                    case R.id.guide:
                        //:TODO guide 눌렀을 때 guide 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(TAG_GUIDE_FRAGMENT, fragmentGuide);
                        break;
                    case R.id.record:
                        //:TODO record 눌렀을 떄 record 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(TAG_RECORD_FRAGMENT, fragmentRecord);
                        break;
                    case R.id.setting:
                        //:TODO setting 눌렀을 때 setting 프래그먼트로 이동하는 이벤트 생성하기
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
                Bundle bundle = new Bundle(1);
                bundle.putString("uid", uid);
                fragmentSetting.setArguments(bundle);
                transaction.show(setting);
            }
        }

        transaction.commitAllowingStateLoss();

    }
}