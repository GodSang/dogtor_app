package com.example.capstone_design;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_dog_name = view.findViewById(R.id.main_dog_name);
        main_dog_age = view.findViewById(R.id.main_dog_age);
        main_dog_gender = view.findViewById(R.id.main_dog_gender);
        main_dog_type = view.findViewById(R.id.main_dog_kind);
        main_dog_weight = view.findViewById(R.id.main_dog_weight);

        home_bundle = getArguments();

        if(home_bundle != null){
            main_dog_name.setText("이름: " + home_bundle.getString("dog_name"));
            main_dog_age.setText("나이: " + home_bundle.getInt("dog_birth"));
            main_dog_gender.setText("성별: " + home_bundle.getString("dog_gender"));
            main_dog_type.setText("견종: " + home_bundle.getString("dog_type"));
            main_dog_weight.setText("몸무게: " + home_bundle.getInt("dog_weight"));
        }

        // 내부 DB 호출
        //SharedPreferences loadShared = getActivity().getSharedPreferences(loadSharedName, Context.MODE_PRIVATE);

        //main_dog_name.setText("이름: " + loadShared.getString("dog_name", "null"));
        //main_dog_age.setText("나이: " + String.valueOf(loadShared.getInt("dog_birth", 0)));
        //main_dog_gender.setText("성별: " + loadShared.getString("dog_gender", "null"));
        //main_dog_type.setText("견종: " + loadShared.getString("dog_type", "null"));
        //main_dog_weight.setText("몸무게: " + String.valueOf(loadShared.getInt("dog_weight", 0)));

    }
}