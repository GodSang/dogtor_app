package com.example.capstone_design.login;

import com.google.gson.annotations.SerializedName;

/*
    JSON데이터들을 class로 받아오기 위한 작업
    즉, JSON데이터들의 KEY값들을 그대로 변수화한다고 생각하면 쉬움.

    JSON 데이터 값과 이름이 정확하게 일치해야함.
    @SerializedName으로 JSON객체를 매칭, 여기서 gson을 사용
*/

public class Post {
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

    //    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public String getBody() {
//        return body;
//    }

}

