package com.nazer.callbacks;

public interface GenericApiCallBack<T> {
    void onSuccess(T t);
    void onFailuire(Throwable throwable);
}
