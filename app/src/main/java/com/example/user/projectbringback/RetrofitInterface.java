package com.example.user.projectbringback;

//import retrofit2.http.GET;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
//import retrofit2.http.PUT;

public interface RetrofitInterface {

    @POST("/user") //가입
    Call<Void> ExsiguUp(@Body  HashMap<String, String> map);

    @POST("/login")
    Call<LoginResult>ExLogin(@Body HashMap<String, String> map);

}