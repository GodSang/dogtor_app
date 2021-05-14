package com.example.capstone_design;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class EditInfoActivity extends AppCompatActivity {

    private ImageView back_btn;
    private CircleImageView edit_profile_image;
    private EditText edit_dog_name;
    private EditText edit_dog_age;
    private EditText edit_dog_weight;
    private Button edit_profile_finish_btn;
    private RadioGroup edit_dog_gender_group;
    private RadioButton edit_dog_gender_man;
    private RadioButton edit_dog_gender_woman;
    private RadioButton edit_dog_gender_none;
    private Spinner edit_dog_type_spinner;

    private CircleImageView profile_image1;
    private CircleImageView profile_image2;
    private CircleImageView profile_image3;
    private CircleImageView selected_profile_image;

    int profile_image_tag = 1;
    String dog_gender;
    String dog_type;

    String token;

    RetrofitClient retrofitClient = new RetrofitClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        SharedPreferences saveShared = this.getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = saveShared.edit();

        SharedPreferences loadShared = getSharedPreferences("DB", MODE_PRIVATE);

        back_btn = findViewById(R.id.back_btn);
        edit_profile_image = findViewById(R.id.edit_profile_image);
        edit_dog_name = findViewById(R.id.edit_dog_name);
        edit_dog_age = findViewById(R.id.edit_dog_age);
        edit_dog_weight = findViewById(R.id.edit_dog_weight);
        edit_profile_finish_btn = findViewById(R.id.edit_profile_finish_btn);
        edit_dog_gender_group = findViewById(R.id.edit_dog_gender_group);
        edit_dog_gender_man = findViewById(R.id.edit_dog_gender_man);
        edit_dog_gender_woman = findViewById(R.id.edit_dog_gender_woman);
        edit_dog_gender_none = findViewById(R.id.edit_dog_gender_none);

        edit_dog_type_spinner = findViewById(R.id.edit_dog_type_spinner);

        token = loadShared.getString("token", "");

        String[] dog_type_spinner_items = getResources().getStringArray(R.array.dog_type_items);
        ArrayAdapter dog_type_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dog_type_spinner_items);
        edit_dog_type_spinner.setAdapter(dog_type_adapter);

        retrofitClient.retrofitGetAPI.getUser(token).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                if (response.isSuccessful()){
                    Data data = response.body();

                    switch (data.getDog_iamge()){
                        case 1:
                            edit_profile_image.setImageResource(R.drawable.dog_profile_1);
                            break;
                        case 2:
                            edit_profile_image.setImageResource(R.drawable.dog_profile_2);
                            break;
                        case 3:
                            edit_profile_image.setImageResource(R.drawable.dog_profile_3);
                            break;
                    }

                    edit_dog_name.setText(data.getDog_name());
                    edit_dog_age.setText(String.valueOf(data.getDog_birth()));

                    switch (data.getDog_gender()){
                        case "M":
                            edit_dog_gender_man.setChecked(true);
                            break;
                        case "W":
                            edit_dog_gender_woman.setChecked(true);
                            break;
                        case "NONE":
                            edit_dog_gender_none.setChecked(true);
                            break;
                    }

                    switch (data.getDog_type()){
                        case "small":
                            edit_dog_type_spinner.setSelection(0);
                            break;
                        case "medium":
                            edit_dog_type_spinner.setSelection(1);
                            break;
                        case "big":
                            edit_dog_type_spinner.setSelection(2);
                            break;
                    }
                    edit_dog_weight.setText(String.valueOf(data.getDog_weight()));
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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_profile_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> input = new HashMap<>();

                input.put("uid", token);
                input.put("dog_name", edit_dog_name.getText().toString());
                input.put("dog_birth", Integer.parseInt(edit_dog_age.getText().toString()));
                input.put("dog_gender", dog_gender);
                input.put("dog_type", dog_type);
                input.put("dog_weight", Integer.parseInt(edit_dog_weight.getText().toString()));
                input.put("dog_image", profile_image_tag);

                sharedEditor.putInt("profile_image", profile_image_tag);
                sharedEditor.commit();

               retrofitClient.retrofitPostAPI.postUserUpdate(token, input).enqueue(new Callback<Data>() {
                   @Override
                   public void onResponse(Call<Data> call, Response<Data> response) {

                       if(response.isSuccessful()){
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

        View v = getLayoutInflater().inflate(R.layout.profile_item, null);
        profile_image1 = v.findViewById(R.id.profile_image1);
        profile_image2 = v.findViewById(R.id.profile_image2);
        profile_image3 = v.findViewById(R.id.profile_image3);
        selected_profile_image = v.findViewById(R.id.selected_profile_image);

        // 프로필 선택시 다이얼로그 실행
        edit_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditInfoActivity.this);

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

                if (v.getParent() != null)
                    ((ViewGroup)v.getParent()).removeView(v);

                dialog.setTitle("프로필 사진 선택");
                dialog.setView(v).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (profile_image_tag){
                            case 1:
                                edit_profile_image.setImageResource(R.drawable.dog_profile_1);
                                break;
                            case 2:
                                edit_profile_image.setImageResource(R.drawable.dog_profile_2);
                                break;
                            case 3:
                                edit_profile_image.setImageResource(R.drawable.dog_profile_3);
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
        edit_dog_gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.edit_dog_gender_man){
                    dog_gender = "M";
                }else if(checkedId == R.id.edit_dog_gender_woman){
                    dog_gender = "W";
                }else{
                    dog_gender = "NONE";
                }
            }
        });

        // 견종 선택 이벤트
        edit_dog_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }
}