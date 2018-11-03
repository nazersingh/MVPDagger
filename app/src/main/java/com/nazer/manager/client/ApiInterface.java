package com.nazer.manager.client;

import com.nazer.pojomodels.ResponsePojo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/users")
    Call<ResponsePojo> createUser(@Body HashMap hashMap);
}
