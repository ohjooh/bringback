package com.example.user.projectbringback;

import com.example.user.projectbringback.data.Set_data;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/user") //가입
    Call<Void> ExsignUp(@Body  HashMap<String, String> map);

    @POST("/login")
    Call<LoginResult>ExLogin(@Body HashMap<String, String> map);

    @POST("/setting")
    Call<Set_data>ExUserData(@Body HashMap<String, String> map);
}