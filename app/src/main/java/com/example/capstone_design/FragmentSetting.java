package com.example.capstone_design;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstone_design.bluetooth.BluetoothActivity;
import com.example.capstone_design.fcm.FcmSettingActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentSetting extends Fragment {

    private TextView setting_change_info;
    private TextView setting_bluetooth;
    private TextView setting_alarm;
    private Button logout_btn;
    private CircleImageView setting_profile_image;
    private TextView registered_pin;

    Context mContext;
    String token;
    int profile_image_tag = 1;
    String TAG = "FragmentSetting";

    private GoogleSignInClient googleSignInClient;
    SharedPreferences loadShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loadShared = this.getActivity().getSharedPreferences("DB", Context.MODE_PRIVATE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        setting_change_info = v.findViewById(R.id.setting_change_info);
        setting_bluetooth = v.findViewById(R.id.setting_bluetooth);
        setting_alarm = v.findViewById(R.id.setting_alarm);
        logout_btn = v.findViewById(R.id.logout_btn);
        setting_profile_image = v.findViewById(R.id.setting_profile_image);
        registered_pin = v.findViewById(R.id.registered_pin);

        token = loadShared.getString("token", "");
        registered_pin.setText(loadShared.getString("PIN", "등록된 PIN번호가 없습니다."));
        profile_image_tag = loadShared.getInt("profile_image" , 1);

        switch (profile_image_tag){
            case 1:
                setting_profile_image.setImageResource(R.drawable.dog_profile_1);
                break;
            case 2:
                setting_profile_image.setImageResource(R.drawable.dog_profile_2);
                break;
            case 3:
                setting_profile_image.setImageResource(R.drawable.dog_profile_3);
                break;
        }


        setting_change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        setting_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BluetoothActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        setting_alarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FcmSettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(mContext, gso);

        // 로그아웃 버튼 기능
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃
                FirebaseAuth.getInstance().signOut();

                // 로그인 초기화(재로그인시에 예전에 로그인 이력이 있는 구글 계정으로 자동 로그인되는 것 방지
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }
        });

        return v;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        profile_image_tag = loadShared.getInt("profile_image" , 1);

        switch (profile_image_tag){
            case 1:
                setting_profile_image.setImageResource(R.drawable.dog_profile_1);
                break;
            case 2:
                setting_profile_image.setImageResource(R.drawable.dog_profile_2);
                break;
            case 3:
                setting_profile_image.setImageResource(R.drawable.dog_profile_3);
                break;
        }
    }
}