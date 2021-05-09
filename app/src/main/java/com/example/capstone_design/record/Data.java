package com.example.capstone_design.record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

// 리사이클러 뷰 아이템 객체
public class Data {
    @SerializedName("amountOfMeal")
    @Expose
    private int amountOfMeal;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("RGB")
    @Expose
    private String RGB;

    public void setAmountOfMeal(int amountOfMeal) {
        this.amountOfMeal = amountOfMeal;
    }

    public int getAmountOfMeal() {
        return amountOfMeal;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setRGB(String RGB) {
        this.RGB = RGB;
    }

    public String getRGB() {
        return RGB;
    }
}
