package com.nazer.manager.dagger;

import com.nazer.manager.client.AllApi;
import com.nazer.manager.client.PaymentApi;
import com.nazer.ui.base.BaseActivity;
import com.nazer.ui.fragments.login.LoginModel;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class,NetModule.class,ViewModule.class,LocalDbModule.class})
public interface ApplicationComponent {

    void Inject(LoginModel loginModel);
    void Inject(AllApi allApi);
    void inject(BaseActivity baseActivity);

    void Inject(PaymentApi paymentApi);

    void inject(NetModule netModule);
}
