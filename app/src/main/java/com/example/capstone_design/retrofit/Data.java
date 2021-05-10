package com.example.capstone_design.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("email")
    private String email;

    @SerializedName("uid")
    private String uid;

    @SerializedName("next")
    private String next;

    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;

    @SerializedName("eat")
    private String eat;

    @SerializedName("color")
    private String color;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String  body;

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

    @SerializedName("key")
    private String key;

    @SerializedName("optionFlag")
    private int optionFlag;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public String getEat() {
        return eat;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
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
