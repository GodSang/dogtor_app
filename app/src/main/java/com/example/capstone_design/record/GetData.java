package com.example.capstone_design.record;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class GetData {
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

    public void setRows(List<Data> rows) {
        this.rows = rows;
    }

    public List<Data> getRows() {
        return rows;
    }
}
