package com.example.assignment_ph26746;

import com.example.assignment_ph26746.Interface.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final  String BaseUrl= "http://192.168.1.9:3000/";

    public static final String url_image="http://192.168.1.9:3000/";
    public static ApiService getService(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);

    }
}
