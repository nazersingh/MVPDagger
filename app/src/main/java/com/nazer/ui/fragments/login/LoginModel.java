package com.nazer.ui.fragments.login;

import com.nazer.callbacks.GenericApiCallBack;
import com.nazer.manager.application.MyApplication;
import com.nazer.manager.client.AllApi;
import com.nazer.pojomodels.ResponsePojo;

import java.util.HashMap;

import javax.inject.Inject;

public class LoginModel {
    @Inject
    AllApi allApi;

    LoginModel() {
        MyApplication.getInstance().getApplicationComponent().Inject(this);
    }

    interface LoginModelCallBack{
        void onSuccess(ResponsePojo response);
        void onFailure(Throwable throwable);
    }

    public void callLoginApi(HashMap hashMap, final LoginModelCallBack loginModelCallBack) {
        allApi.callLoginApi(hashMap, new GenericApiCallBack<ResponsePojo>() {
            @Override
            public void onSuccess(ResponsePojo response) {
                loginModelCallBack.onSuccess(response);
            }

            @Override
            public void onFailuire(Throwable throwable) {
                loginModelCallBack.onFailure(throwable);
            }
        });
    }
}
