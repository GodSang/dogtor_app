package com.example.capstone_design.retrofit;

import com.example.capstone_design.record.Data;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecyclerViewData {
    @SerializedName("count")
    private int count;
    @SerializedName("rows")
    private List<Data> rows;


    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setRows(List<com.example.capstone_design.record.Data> rows) {
        this.rows = rows;
    }

    public List<Data> getRows() {
        return rows;
    }
}
