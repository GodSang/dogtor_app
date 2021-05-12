package com.example.capstone_design.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_design.R;
import com.example.capstone_design.retrofit.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<Data> list = new ArrayList<Data>();
    String TAG = "";

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");


    public RecyclerViewAdapter(List<Data> data) {
        this.list = data;
    }

    public void modifyFlag(String tag){
        this.TAG = tag; // FragmentRecord에서 어떤 버튼 클릭 했는지에 대한 태그
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
            return new CustomViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CustomViewHolder){
            ItemRows((CustomViewHolder) holder,  position);
        }else if(holder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder)holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    private void showLoadingView(LoadingViewHolder holder, int position){

    }

    private void ItemRows(CustomViewHolder holder, int position) {
        Data item = list.get(position);

        holder.intake_or_color.setText("");
        holder.intake_or_color.setBackgroundColor(Color.parseColor("#ffffff"));

        if(TAG.equals("INTAKE")){ // 사료량에 대한 아이템 값 대입
            holder.intake_or_color.setText(String.valueOf(item.getAmountOfMeal()) + "g");
            holder.createdAt.setText(transFormat.format(item.getCreatedAt()));
        }

        if(TAG.equals("PEE")){ // 대소변 정보에 대한 아이템 값 대입
            holder.intake_or_color.setBackgroundColor(Color.parseColor(item.getRGB()));
            holder.createdAt.setText(transFormat.format(item.getCreatedAt()));
        }

        if(TAG.equals("POO")){ // 대소변 정보에 대한 아이템 값 대입
            holder.intake_or_color.setBackgroundColor(Color.parseColor(item.getRGB()));
            holder.createdAt.setText(transFormat.format(item.getCreatedAt()));
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder{

        private TextView intake_or_color;
        private TextView createdAt;

        public CustomViewHolder(View itemView) {
            super(itemView);
            intake_or_color = itemView.findViewById(R.id.item_intake_or_color);
            createdAt = itemView.findViewById(R.id.item_createdAt);
        }

        public void setIntake_or_color(TextView intake_or_color) {
            this.intake_or_color = intake_or_color;
        }

        public void setCreatedAt(TextView createdAt) {
            this.createdAt = createdAt;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
