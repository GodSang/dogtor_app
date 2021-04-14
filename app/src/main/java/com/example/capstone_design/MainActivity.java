package com.example.capstone_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final int Fragment_main_home = 1;
    private final int Fragment_guide = 2;
    private final int Fragment_record = 3;
    private final int Fragment_setting = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new FragmentMainHome()).commit();

        /*supportFragmentManager.beginTransaction().add(R.id.linearLayout, HomeFragment()).commit()
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentMainHome fragmentMainHome = new FragmentMainHome();
        transaction.replace(R.id.fragment, fragmentMainHome);
        transaction.commit();*/

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_home:
                        //:TODO home 눌렀을 때 home 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(Fragment_main_home);
                        break;
                    case R.id.guide:
                        //:TODO guide 눌렀을 때 guide 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(Fragment_guide);
                        break;
                    case R.id.record:
                        //:TODO record 눌렀을 떄 record 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(Fragment_record);
                        break;
                    case R.id.setting:
                        //:TODO setting 눌렀을 때 setting 프래그먼트로 이동하는 이벤트 생성하기
                        FragmentView(Fragment_setting);
                        break;
                }

                return true;
            }
        });
    }

    private void FragmentView(int fragment){
        //FragmentTransaction를 이용해 프래그먼트를 사용합니다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment){
            case 1:
                // 메인 홈 프래그먼트 호출
                FragmentMainHome fragmentMainHome = new FragmentMainHome();
                transaction.replace(R.id.fragment, fragmentMainHome);
                transaction.commit();
                break;

            case 2:
                // 가이드 프래그먼트 호출
                FragmentGuide fragmentGuide = new FragmentGuide();
                transaction.replace(R.id.fragment, fragmentGuide);
                transaction.commit();
                break;
            case 3:
                // 기록 프래그먼트 호출
                FragmentRecord fragmentRecord = new FragmentRecord();
                transaction.replace(R.id.fragment, fragmentRecord);
                transaction.commit();
                break;
            case 4:
                // 설정 프래그먼트 호출
                FragmentSetting fragmentSetting = new FragmentSetting();
                transaction.replace(R.id.fragment, fragmentSetting);
                transaction.commit();
                break;
        }

    }
}