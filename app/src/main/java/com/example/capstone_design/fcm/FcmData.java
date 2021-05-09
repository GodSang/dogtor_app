package com.example.capstone_design.fcm;

import com.google.gson.annotations.SerializedName;

public class FcmData {
    @SerializedName("key")
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
