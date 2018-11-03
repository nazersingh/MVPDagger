package com.nazer.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;


import com.nazer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {


    @BindView(R.id.text_terms_and_conditions)
    TextView mTvTermsAndConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setSpannable();
    }

    private void setSpannable() {

        SpannableStringBuilder spannableString = new SpannableStringBuilder(getString(R.string.i_agree_to_the_terms));
        ClickableSpan clickableTermsConditions = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, TermsConditionsActivity.class));

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickablePrivacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, PrivacyPolicyActivity.class));

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(clickableTermsConditions, 15, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickablePrivacy, 40, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_orange)), 15, 35, 0);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_orange)), 40, 54, 0);
        mTvTermsAndConditions.setText(spannableString);
        mTvTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());
        mTvTermsAndConditions.setHighlightColor(Color.TRANSPARENT);
    }
}
