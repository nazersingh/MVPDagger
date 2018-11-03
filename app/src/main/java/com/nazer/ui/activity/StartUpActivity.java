package com.nazer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nazer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartUpActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_signup)
    Button mBtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(StartUpActivity.this,LoginActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(StartUpActivity.this,SignUpActivity.class));
                break;
        }
    }
}
