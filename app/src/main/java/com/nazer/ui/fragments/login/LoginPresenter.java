package com.nazer.ui.fragments.login;

import com.nazer.pojomodels.ResponsePojo;

import java.util.HashMap;

class LoginPresenter {

    private LoginModel loginModel = new LoginModel();
    private LoginPresenterCallBack loginPresenterCallBack;

    LoginPresenter(LoginPresenterCallBack loginPresenterCallBack) {
        this.loginPresenterCallBack = loginPresenterCallBack;
    }

    public void callLogin(HashMap hashMap) {
        loginModel.callLoginApi(hashMap, loginModelCallBack);
    }

    /**
     * ========= LoginModelCallBack received the data
     */
    LoginModel.LoginModelCallBack loginModelCallBack = new LoginModel.LoginModelCallBack() {
        @Override
        public void onSuccess(ResponsePojo response) {
            loginPresenterCallBack.onSuccess(response);
        }

        @Override
        public void onFailure(Throwable throwable) {
            loginPresenterCallBack.onFailure(throwable);
        }
    };

    interface LoginPresenterCallBack {
        void onSuccess(ResponsePojo response);
        void onFailure(Throwable throwable);
    }


}
