package com.example.capstone_design;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfoActivity extends AppCompatActivity {

    private ImageView back_btn;
    private CircleImageView edit_profile_image;
    private EditText edit_dog_name;
    private EditText edit_dog_age;
    private EditText edit_dog_gender;
    private EditText edit_dog_type;
    private Button edit_profile_finish_btn;

    private CircleImageView profile_image1;
    private CircleImageView profile_image2;
    private CircleImageView profile_image3;
    private CircleImageView selected_profile_image;

    String profile_image_tag = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        back_btn = findViewById(R.id.back_btn);
        edit_dog_name = findViewById(R.id.edit_dog_name);
        edit_dog_age = findViewById(R.id.edit_dog_age);
        edit_dog_gender = findViewById(R.id.edit_dog_gender);
        edit_dog_type = findViewById(R.id.edit_dog_type);
        edit_profile_finish_btn = findViewById(R.id.edit_profile_finish_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_profile_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        profile_image_tag = "1";
                    }
                });

                profile_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_profile_image.setImageResource(R.drawable.dog_profile_2);
                        profile_image_tag = "2";
                    }
                });

                profile_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selected_profile_image.setImageResource(R.drawable.dog_profile_3);
                        profile_image_tag = "3";
                    }
                });

                if (v.getParent() != null)
                    ((ViewGroup)v.getParent()).removeView(v);

                dialog.setTitle("프로필 사진 선택");
                dialog.setView(v).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (profile_image_tag){
                            case "1":
                                edit_profile_image.setImageResource(R.drawable.dog_profile_1);
                                break;
                            case "2":
                                edit_profile_image.setImageResource(R.drawable.dog_profile_2);
                                break;
                            case "3":
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

    }
}