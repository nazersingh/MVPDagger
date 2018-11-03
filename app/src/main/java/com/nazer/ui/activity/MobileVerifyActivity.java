package com.nazer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.nazer.R;
import com.nazer.util.GenericTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileVerifyActivity extends AppCompatActivity {


    @BindView(R.id.tv_reg_num)
    TextView mTvRegNum;
    @BindView(R.id.tv_resend_code)
    TextView mTvResendCode;
    @BindView(R.id.btn_vrfy_mbl_nxt)
    Button mBtnVrfyMblNxt;
    @BindView(R.id.edt_verify_1)
    EditText mEdtVerify1;
    @BindView(R.id.edt_verify_2)
    EditText mEdtVerify2;
    @BindView(R.id.edt_verify_3)
    EditText mEdtVerify3;
    @BindView(R.id.edt_verify_4)
    EditText mEdtVerify4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);
        ButterKnife.bind(this);

        mEdtVerify1.addTextChangedListener(GenericTextWatcher.getInstance(mEdtVerify1,mEdtVerify2,mEdtVerify3,mEdtVerify4));
        mEdtVerify2.addTextChangedListener(GenericTextWatcher.getInstance(mEdtVerify1,mEdtVerify2,mEdtVerify3,mEdtVerify4));
        mEdtVerify3.addTextChangedListener(GenericTextWatcher.getInstance(mEdtVerify1,mEdtVerify2,mEdtVerify3,mEdtVerify4));
        mEdtVerify4.addTextChangedListener(GenericTextWatcher.getInstance(mEdtVerify1,mEdtVerify2,mEdtVerify3,mEdtVerify4));
//

//        Util.onTextCahangeListener(mEdtVerify1,null,new )

    }

    @OnClick({ R.id.btn_vrfy_mbl_nxt})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_vrfy_mbl_nxt:
                Intent intent = new Intent(MobileVerifyActivity.this, InboxActivity.class);
                startActivity(intent);
                break;
        }
    }


}
