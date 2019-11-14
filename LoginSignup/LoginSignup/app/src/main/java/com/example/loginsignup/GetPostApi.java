package com.example.loginsignup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetPostApi {
    @FormUrlEncoded
    @POST("register")
    Call<SignUpPost> createPost(
            @Query("name") String name,
            @Query("mobile") String mobile,
            @Query("password") String password,
            @Query("email") String email);

    @POST("login")
    Call<SignInGet> createGetPost(
            @Query("password") String password,
            @Query("email") String email);
}
