package com.example.capstone_design;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.capstone_design.adapter.ViewPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class FragmentWeight extends Fragment {

    Activity activity;
    Context context;
    ViewPagerAdapter adapter;
    ViewPager viewPager;
    CircleIndicator indicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        activity = getActivity();
        context = getContext();

        viewPager = view.findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(context);
        viewPager.setAdapter(adapter);
        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        return view;
    }
}