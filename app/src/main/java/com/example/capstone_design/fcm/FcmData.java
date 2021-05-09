package com.example.capstone_design.fcm;

import com.google.gson.annotations.SerializedName;

public class FcmData {
    @SerializedName("key")
    private String key;
    @SerializedName("optionFlag")
    private int optionFlag;

    public void setKey(String key) {
        this.key = key;
    }

    public void setOptionFlag(int optionFlag) {
        this.optionFlag = optionFlag;
    }

    public String getKey() {
        return key;
    }

    public int getOptionFlag() {
        return optionFlag;
    }
}
