package com.nazer.manager.client;

import com.nazer.callbacks.GenericApiCallBack;
import com.nazer.manager.application.MyApplication;
import com.nazer.pojomodels.ResponsePojo;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllApi {

    @Inject
    ApiInterface apiInterface;



    ArrayList<Call> calls = new ArrayList<>();

    public AllApi() {
        MyApplication.getInstance().getApplicationComponent().Inject(this);
    }

    /**
     * @param hashMap
     * @param apiCallBack
     */
    public void callLoginApi(HashMap hashMap, final GenericApiCallBack<ResponsePojo> apiCallBack) {
        Call<ResponsePojo> call = apiInterface.createUser(hashMap);
        calls.add(call);
        call.enqueue(new Callback<ResponsePojo>() {
            @Override
            public void onResponse(Call<ResponsePojo> call, Response<ResponsePojo> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponsePojo> call, Throwable t) {
                call.cancel();
                apiCallBack.onFailuire(t);
            }
        });
    }

}
