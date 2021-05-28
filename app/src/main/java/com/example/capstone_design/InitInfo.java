package com.example.capstone_design;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstone_design.retrofit.Data;
import com.example.capstone_design.retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitInfo extends AppCompatActivity {

    String uid;
    Intent intent_main;

    Button dog_service_start;
    CircleImageView dog_image;
    EditText dog_name_ed;
    EditText dog_birth_ed;
    EditText dog_weight_ed;
    EditText dog_kcal_ed;

    Spinner dog_type_spinner;

    RadioGroup dog_gender_group;
    String dog_gender = "M";
    String dog_type = "소형견";

    RetrofitClient retrofitClient = new RetrofitClient();

    private CircleImageView profile_image1;
    private CircleImageView profile_image2;
    private CircleImageView profile_image3;
    private CircleImageView selected_profile_image;

    int profile_image_tag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_info);

        //uid 가져오기
        Intent get_uid_intent = getIntent();
        uid = get_uid_intent.getStringExtra("uid");

        SharedPreferences saveShared = this.getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = saveShared.edit();

        dog_service_start = findViewById(R.id.dog_service_start);
        dog_image = findViewById(R.id.dog_image);
        dog_name_ed = findViewById(R.id.dog_name);
        dog_birth_ed = findViewById(R.id.dog_age); // dog_birth // Integer
        dog_weight_ed = findViewById(R.id.dog_weight); // dog_weight // integer
        dog_gender_group = findViewById(R.id.dog_gender_group);
        dog_type_spinner = findViewById(R.id.dog_type_spinner);
        dog_kcal_ed = findViewById(R.id.dog_kcal);

        String[] dog_type_spinner_items = getResources().getStringArray(R.array.dog_type_items);
        ArrayAdapter dog_type_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dog_type_spinner_items);
        dog_type_spinner.setAdapter(dog_type_adapter);

        View v = getLayoutInflater().inflate(R.layout.profile_item, null);
        profile_image1 = v.findViewById(R.id.profile_image1);
        profile_image2 = v.findViewById(R.id.profile_image2);
        profile_image3 = v.findViewById(R.id.profile_image3);
        selected_profile_image = v.findViewById(R.id.selected_profile_image);

        // 프로필 선택시 다이얼로그 실행
        dog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(InitInfo.this);

                profile_image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_profile_image.setImageResource(R.drawable.dog_profile_1);
                        profile_image_tag = 1;
                    }
                });

                profile_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_profile_image.setImageResource(R.drawable.dog_profile_2);
                        profile_image_tag = 2;
                    }
                });

                profile_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_profile_image.setImageResource(R.drawable.dog_profile_3);
                        profile_image_tag = 3;
                    }
                });

                // 다이얼로그 여러번 생성 시(즉, 한 번 이상 프로필 이미지 선택해서 다이얼로그가 여러 번 띄워질 시
                // 뷰가 그 횟수만큼 생기기 때문에 오류가 발생해서 중복 된 뷰 삭제 하는 부분
                if (v.getParent() != null)
                    ((ViewGroup)v.getParent()).removeView(v);

                dialog.setTitle("프로필 사진 선택");
                dialog.setView(v).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (profile_image_tag){
                            case 1:
                                dog_image.setImageResource(R.drawable.dog_profile_1);
                                break;
                            case 2:
                                dog_image.setImageResource(R.drawable.dog_profile_2);
                                break;
                            case 3:
                                dog_image.setImageResource(R.drawable.dog_profile_3);
                                break;
                        }
                    }
                })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                dialog.show();
            }
        });

        // 성별 선택 이벤트
        dog_gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.dog_gender_man){
                    dog_gender = "M";
                }else if(checkedId == R.id.dog_gender_woman){
                    dog_gender = "W";
                }else{
                    dog_gender = "NONE";
                }
            }
        });

        // 견종 선택 이벤트
        dog_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        dog_type = "small";
                        break;
                    case 1:
                        dog_type = "medium";
                        break;
                    case 2:
                        dog_type = "big";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dog_service_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                HashMap<String, Object> input = new HashMap<>();

                input.put("uid", uid);
                input.put("dog_name", dog_name_ed.getText().toString());
                input.put("dog_birth", Integer.parseInt(dog_birth_ed.getText().toString()));
                input.put("dog_gender", dog_gender);
                input.put("dog_type", dog_type);
                input.put("dog_weight", Integer.parseInt(dog_weight_ed.getText().toString()));
                input.put("dog_image", profile_image_tag);
                input.put("dog_kcal", Integer.parseInt(dog_kcal_ed.getText().toString()));

                retrofitClient.retrofitPostAPI.postUser(input).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(response.isSuccessful()){
                            Data data = response.body();

                            sharedEditor.putString("token", data.getToken());
                            sharedEditor.putInt("profile_image", profile_image_tag);
                            sharedEditor.commit();

                            intent_main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent_main);

                            finish();

                        }else if(response.code() == 400){
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

    }
}