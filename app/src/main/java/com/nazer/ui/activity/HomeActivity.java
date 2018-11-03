package com.nazer.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.nazer.R;
import com.nazer.ui.base.BaseActivity;
import com.nazer.ui.fragments.login.LoginFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        setOptionMenu(R.menu.home);
        setUpLayout();
    }

    @Override
    public void setUpLayout() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onAddFragment(new LoginFragment());
            }
        },5000);
    }

    @Override
    public void setUpView() {

    }
}
