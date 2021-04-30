package com.example.capstone_design;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstone_design.bluetooth.BluetoothActivity;


public class FragmentSetting extends Fragment {

    private TextView setting_change_info;
    private TextView setting_bluetooth;
    Bundle bundle; // uid 값 가져올 bundle
    String uid;
    String TAG = "FragmentSetting";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        setting_change_info = v.findViewById(R.id.setting_change_info);
        setting_bluetooth = v.findViewById(R.id.setting_bluetooth);

        bundle = getArguments();

        if(bundle != null){
            uid = bundle.getString("uid");
        }

        Log.d(TAG, "UID: " + uid);

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
                intent.putExtra("uid", uid);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        return v;

    }

}