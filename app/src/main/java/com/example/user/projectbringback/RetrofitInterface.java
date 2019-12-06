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

//    @GET('/user') //로그인
//    @PUT('/user') //수정
//    @GET('/music/음악제목변수')//음악정보 가져옴
//    @GET('music/음악제목변수/play')//노래 재생



}