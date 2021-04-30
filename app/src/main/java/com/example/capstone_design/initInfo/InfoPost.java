package com.example.capstone_design.initInfo;

import com.google.gson.annotations.SerializedName;

/*
    JSON데이터들을 class로 받아오기 위한 작업
    즉, JSON데이터들의 KEY값들을 그대로 변수화한다고 생각하면 쉬움.

    JSON 데이터 값과 이름이 정확하게 일치해야함.
    @SerializedName으로 JSON객체를 매칭, 여기서 gson을 사용
*/

public class InfoPost {
    @SerializedName("uid")
    private String uid;
    @SerializedName("dog_image")
    private String dog_iamge;
    @SerializedName("dog_name")
    private String dog_name;
    @SerializedName("dog_birth")
    private Integer dog_birth;
    @SerializedName("dog_type")
    private String dog_type;
    @SerializedName("dog_gender")
    private String dog_gender;
    @SerializedName("dog_weight")
    private Integer dog_weight;


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setDog_iamge(String dog_iamge) {
        this.dog_iamge = dog_iamge;
    }

    public String getDog_iamge() {
        return dog_iamge;
    }

    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }

    public String getDog_name() {
        return dog_name;
    }

    public void setDog_birth(Integer dog_birth) {
        this.dog_birth = dog_birth;
    }

    public Integer getDog_birth() {
        return dog_birth;
    }

    public void setDog_type(String dog_type) {
        this.dog_type = dog_type;
    }

    public String getDog_type() {
        return dog_type;
    }

    public void setDog_gender(String dog_gender) {
        this.dog_gender = dog_gender;
    }

    public String getDog_gender() {
        return dog_gender;
    }

    public void setDog_weight(Integer dog_weight) {
        this.dog_weight = dog_weight;
    }

    public Integer getDog_weight() {
        return dog_weight;
    }
}


