package com.example.loginsignup;

import com.google.gson.annotations.SerializedName;

public class SignUpPost {
    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public SignUpPost(String name, String mobile, String password, String email) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
