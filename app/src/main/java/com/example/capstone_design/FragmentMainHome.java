package com.example.capstone_design;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FragmentMainHome extends Fragment {

    TextView main_dog_name;
    TextView main_dog_age;
    TextView main_dog_gender;
    TextView main_dog_type;
    TextView main_dog_weight;

    // 내부 DB 데이터 이름
    //String loadSharedName = "dog_info";

    // 메인 액티비티에서 GET 요청을 통해 회원가입 데이터 받아오기
    Bundle home_bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        main_dog_name = view.findViewById(R.id.main_dog_name);
        main_dog_age = view.findViewById(R.id.main_dog_age);
        main_dog_gender = view.findViewById(R.id.main_dog_gender);
        main_dog_type = view.findViewById(R.id.main_dog_kind);
        main_dog_weight = view.findViewById(R.id.main_dog_weight);

        home_bundle = getArguments();
        Log.d("hyeals_bundle_fragment", "프래그먼트 실행");

        if(home_bundle != null){
            Log.d("hyeals_bundle_fragment", "여기 실행");
            Log.d("hyeals_bundle_fragment", home_bundle.getString("dog_name"));
            main_dog_name.setText("이름: " + home_bundle.getString("dog_name"));
            main_dog_age.setText("나이: " + home_bundle.getInt("dog_birth"));
            main_dog_gender.setText("성별: " + home_bundle.getString("dog_gender"));
            main_dog_type.setText("견종: " + home_bundle.getString("dog_type"));
            main_dog_weight.setText("몸무게: " + home_bundle.getInt("dog_weight"));
        }

        return view;
    }
}