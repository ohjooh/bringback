package com.example.user.projectbringback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static Retrofit retrofit;
    public static RetrofitInterface retrofitInterface;
    public static String BASE_URL = "http://503a8584.ngrok.io";
    //http://10.0.2.2:3000 에뮬레이터 실행 시 주소

    public static void onNetwork() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }


}