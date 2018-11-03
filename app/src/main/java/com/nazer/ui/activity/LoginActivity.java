package com.nazer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nazer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.linearNotMember)
    LinearLayout mLnrNotMember;
    @BindView(R.id.edit_text_email)
    TextInputEditText mEdtTextEmail;
    @BindView(R.id.textInputEmail)
    TextInputLayout mIptEmailLayout;
    @BindView(R.id.edit_text_password)
    TextInputEditText mEdtTextPassword;
    @BindView(R.id.textInputPassword)
    TextInputLayout mIptPassLayout;
    @BindView(R.id.text_forgot_password)
    TextView mTvForgotPassword;
    @BindView(R.id.button_login)
    Button mBtnLogin;
    @BindView(R.id.fb_login)
    ImageView mIvFbLogin;
    @BindView(R.id.google_login)
    ImageView mIvGoogleLogin;
    @BindView(R.id.linkedin_login)
    ImageView mIvLinkedinLogin;
    @BindView(R.id.text_sign_up_now)
    TextView mTvSignUpNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.linearNotMember)
    public void onViewClicked() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

    }

    @OnClick({R.id.text_forgot_password, R.id.button_login, R.id.fb_login, R.id.google_login, R.id.linkedin_login, R.id.text_sign_up_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
            case R.id.button_login:
                startActivity(new Intent(LoginActivity.this,PersonalDetailsActivity.class));
                break;
            case R.id.fb_login:
                break;
            case R.id.google_login:
                break;
            case R.id.linkedin_login:
                break;
            case R.id.text_sign_up_now:
                break;
        }
    }
}
