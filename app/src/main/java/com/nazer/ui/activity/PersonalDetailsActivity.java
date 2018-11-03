package com.nazer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nazer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_user)
    CircleImageView mIvUser;
    @BindView(R.id.iv_select_image)
    CircleImageView mIvSelectImage;
    @BindView(R.id.edt_frst_name)
    EditText mEdtFrstName;
    @BindView(R.id.edt_lst_name)
    EditText mEdtLstName;
    @BindView(R.id.edt_phn_num)
    EditText mEdtPhnNum;
    @BindView(R.id.edt_post_cde)
    EditText mEdtPostCde;
    @BindView(R.id.tv_char_left)
    TextView mTvCharLeft;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.edt_desc)
    EditText mEdtDesc;
    @BindView(R.id.tv_verify)
    TextView mTvVerify;
    @BindView(R.id.rel_prsnl_details)
    RelativeLayout relPrsnlDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        ButterKnife.bind(this);
        mIvBack.setVisibility(View.GONE);
        mEdtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mTvCharLeft.setText(+(300 - s.length()) + " char left");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @OnClick(R.id.btn_next)
    public void onNextClicked() {
        Intent intent = new Intent(this, InboxActivity.class);
        startActivity(intent);

    }


    @OnClick(R.id.tv_verify)
    public void onViewClicked() {
        Intent intent = new Intent(PersonalDetailsActivity.this, MobileVerifyActivity.class);
        startActivity(intent);
    }
}
