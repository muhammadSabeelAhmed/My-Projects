package com.example.loginsignup;

import com.google.gson.annotations.SerializedName;

class SignInGet {
    @SerializedName("email")
    private String email;
    @SerializedName("message")
    private String message;
    
    @SerializedName("password")
    private String password;

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
