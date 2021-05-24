package com.example.capstone_design;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.capstone_design.adapter.ViewPagerAdapter;
import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWeight extends Fragment {
    String token;   // header에 넣을 JWT 저장 할 변수

    // viewpager slide를 쓰기 위한 변수 선언
    Activity activity;
    Context context;
    ViewPagerAdapter adapter;
    ViewPager viewPager;
    CircleIndicator indicator;  // 사진 밑에서 몇번 째 사진인지 나타내주는 indicator

    // 권장 몸무게 계산 위한 변수 선언
    private int dog_status = 0; // 강아지의 현재 상태 (User가 spinner를 통해 입력)
    private Button dog_status_btn;  // 강아지의 권장 몸무게 계산해주는 버튼 (GET)
    private Spinner dog_status_spinner; // 강아지 현재 상태 입력 받는 spinner
    private TextView fat_result_tv; // 결과 메세지를 받아서 출력해주는 textView

    RetrofitClient retrofitClient = new RetrofitClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences loadShared = this.getActivity().getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = loadShared.edit();
        token = loadShared.getString("token", "");

        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        activity = getActivity();
        context = getContext();

        viewPager = view.findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(context);
        viewPager.setAdapter(adapter);
        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        String[] dog_status_spinner_items = getResources().getStringArray(R.array.dog_status_items);
        dog_status_spinner = view.findViewById(R.id.weight_spinner);
        ArrayAdapter dog_type_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, dog_status_spinner_items);
        dog_status_spinner.setAdapter(dog_type_adapter);
        dog_status_btn = view.findViewById(R.id.weight_button);
        fat_result_tv = view.findViewById(R.id.weight_textView);

        dog_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        dog_status = 1;
                        break;
                    case 1:
                        dog_status = 2;
                        break;
                    case 2:
                        dog_status = 3;
                        break;
                    case 3:
                        dog_status = 4;
                        break;
                    case 4:
                        dog_status = 5;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dog_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retrofitClient.retrofitGetAPI.getFat(token, dog_status).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        Data data = response.body();
                        String proper_weight_tv_string;
                        proper_weight_tv_string = print_status(data.getDog_weight(), data.getDogFat());
                        fat_result_tv.setText(proper_weight_tv_string);
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        return view;
    }

    private String print_status(int weight, float fat) {
        String str;
        str = "강아지의 현재 몸무게는 " + weight + "kg 입니다.\n"
                + "해당 강아지의 권장 몸무게는 " + fat + "kg 입니다.";
        return str;
    }
}