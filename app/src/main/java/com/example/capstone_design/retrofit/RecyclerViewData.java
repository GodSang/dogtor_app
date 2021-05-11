package com.example.capstone_design.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecyclerViewData {
    @SerializedName("rows")
    private List<Data> rows;

    public void setRows(List<Data> rows) {
        this.rows = rows;
    }

    public List<Data> getRows() {
        return rows;
    }
}
