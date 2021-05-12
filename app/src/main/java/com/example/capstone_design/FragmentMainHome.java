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

import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMainHome extends Fragment {

    CircleImageView main_dog_profile_image;
    TextView main_dog_name;
    TextView main_dog_age;
    TextView main_dog_gender;
    TextView main_dog_type;
    TextView main_dog_weight;
    TextView dog_status_info;
    TextView dog_status_info_time;

    // SharedPreferences를 통해 fcm body 데이터 가져오기
    String loadSharedName = "FCM_DB";
    String fcm_body = "";
    String fcm_time = "";

    RetrofitClient retrofitClient = new RetrofitClient();

    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        SharedPreferences loadShared = this.getActivity().getSharedPreferences("DB", Context.MODE_PRIVATE);
        token = loadShared.getString("token", "");

        main_dog_name = view.findViewById(R.id.main_dog_name);
        main_dog_age = view.findViewById(R.id.main_dog_age);
        main_dog_gender = view.findViewById(R.id.main_dog_gender);
        main_dog_type = view.findViewById(R.id.main_dog_kind);
        main_dog_weight = view.findViewById(R.id.main_dog_weight);
        main_dog_profile_image = view.findViewById(R.id.main_dog_profile_image);
        //dog_status_info = view.findViewById(R.id.dog_status_info);
       // dog_status_info_time = view.findViewById(R.id.dog_status_info_time);

        Log.d("hyeals_bundle_fragment", "프래그먼트 실행");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences loadShared = this.getActivity().getSharedPreferences(loadSharedName, Context.MODE_PRIVATE);
        fcm_body = loadShared.getString("fcm_body", "");
        fcm_time = loadShared.getString("fcm_time", "");

        Log.d("fcm_body", "fcm body: " + fcm_body);

        if(!fcm_body.equals("")){
            dog_status_info.setText(fcm_body);
            dog_status_info_time.setText(fcm_time);
        }

        // GET 요청
        retrofitClient.retrofitGetAPI.getUser(token).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Data data = response.body();

                    Log.d("hyeals_bundle", data.getDog_name());

                    main_dog_name.setText("이름: " + data.getDog_name());
                    main_dog_age.setText("나이: " + String.valueOf(data.getDog_birth()));
                    main_dog_gender.setText("성별: " + data.getDog_gender());
                    main_dog_type.setText("견종: " + data.getDog_gender());
                    main_dog_weight.setText("몸무게: " + String.valueOf(data.getDog_weight()));

                    switch(data.getDog_iamge())
                    {
                        case 1:
                            main_dog_profile_image.setImageResource(R.drawable.dog_profile_1);
                            break;
                        case 2:
                            main_dog_profile_image.setImageResource(R.drawable.dog_profile_2);
                            break;
                        case 3:
                            main_dog_profile_image.setImageResource(R.drawable.dog_profile_3);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}