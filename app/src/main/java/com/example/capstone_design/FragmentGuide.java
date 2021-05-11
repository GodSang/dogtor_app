package com.example.capstone_design;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentGuide extends Fragment {

    private LinearLayout pee_color_guide_btn;
    private LinearLayout pee_color_guide;
    private LinearLayout poo_color_guide_btn;
    private LinearLayout poo_color_guide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_guide, container, false);
        pee_color_guide_btn = v.findViewById(R.id.pee_color_guide_btn);
        pee_color_guide = v.findViewById(R.id.pee_color_guide);
        poo_color_guide_btn = v.findViewById(R.id.poo_color_guide_btn);
        poo_color_guide = v.findViewById(R.id.poo_color_guide);

        pee_color_guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pee_color_guide.getVisibility() == View.INVISIBLE){
                    pee_color_guide.setVisibility(View.VISIBLE);
                }else{
                    pee_color_guide.setVisibility(View.INVISIBLE);
                }
            }
        });

        poo_color_guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(poo_color_guide.getVisibility() == View.INVISIBLE){
                    poo_color_guide.setVisibility(View.VISIBLE);
                }else{
                    poo_color_guide.setVisibility(View.INVISIBLE);
                }
            }
        });

        return v;
    }

}