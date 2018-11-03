package com.nazer.util;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.nazer.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {


    public  boolean isValidEmail(Context context, EditText email, TextInputLayout textInputLayout) {
        return isValidEmailCheck(context, email, textInputLayout, context.getString(R.string.err_enter_email));
    }

    public  boolean checkEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public  boolean isValidEmailCheck(Context context, EditText email, TextInputLayout textInputLayout, String msg) {
        if (isNullorEmpty(email, textInputLayout, msg))
            return false;
        if (!checkEmail(email.getText().toString())) {
            setError(email, textInputLayout, context.getString(R.string.err_valid_email));
            return false;
        }
        if (!isContainSpace(email, textInputLayout, context.getString(R.string.err_msg_space)))
            return false;

        return true;
    }


    public  boolean isValidMobile(Context context, String countryCode, EditText mobileNumber, TextInputLayout textInputLayout) {
        boolean isValid = false;
        if (!isNullorEmpty(mobileNumber, textInputLayout, context.getString(R.string.err_enter_mobile_number))
                && isContainSpace(mobileNumber, textInputLayout, context.getString(R.string.err_valid_mobile_number))
                && !checkPattren(mobileNumber, textInputLayout, context.getString(R.string.err_valid_mobile_number)))
            return true;
        else
            return isValid;
    }


    /**
     * check for email and password
     */

    public boolean isPasswordValid(Context activity, EditText mpassword, TextInputLayout textInputLayout, String msg) {

        if (isNullorEmpty(mpassword, textInputLayout, msg)) {
            return false;
        } else if (!checkPasswordLength(activity, mpassword, textInputLayout)) {
            return false;
        } else if (!isContainSpace(mpassword, textInputLayout, activity.getString(R.string.err_msg_space))) {
            return false;
        }
        return true;
    }

    public boolean checkPasswordLength(Context context, EditText mEditOldPassword, TextInputLayout textOldPassword) {
        if (mEditOldPassword.length() < 6) {
            setError(mEditOldPassword, textOldPassword, context.getString(R.string.err_password_length_more));
            return false;
        } else if (mEditOldPassword.length() > 15) {
            setError(mEditOldPassword, textOldPassword, context.getString(R.string.err_password_length_more));
            return false;
        }
        return true;
    }

    public boolean isChangePassword(Context activity, EditText mEditOldPassword, TextInputLayout textOldPassword, EditText mEditNewPassword, TextInputLayout textNewPassword, EditText mEditConfirmPassword, TextInputLayout textConfirmPassword) {

        if (!isPasswordValid(activity, mEditOldPassword, textOldPassword, activity.getString(R.string.err_enter_old_password))) {
            return false;
        }
        if (!isPasswordValid(activity, mEditNewPassword, textNewPassword, activity.getString(R.string.err_enter_new_password))) {
            return false;
        }
        if (!isPasswordValid(activity, mEditConfirmPassword, textConfirmPassword, activity.getString(R.string.err_msg_confirmpassword))) {
            return false;
        }
        if (mEditOldPassword.getText().toString().equalsIgnoreCase(mEditNewPassword.getText().toString())) {
            setError(mEditNewPassword, textNewPassword, activity.getString(R.string.err_msg_confirmpassword_not_match));
            return false;
        }
        if (!mEditConfirmPassword.getText().toString().equalsIgnoreCase(mEditNewPassword.getText().toString())) {
            setError(mEditConfirmPassword, textConfirmPassword, activity.getString(R.string.err_msg_confirmpassword_not_match));
            return false;
        }

        return true;

    }


    /**
     * To check whether a given edittext is null or empty
     *
     * @param editText
     * @return status of edittext
     */
    public  boolean isNullorEmpty(EditText editText, TextInputLayout textInputLayout, String errorMsg) {
        if (TextUtils.isEmpty(editText.getText())) {
            setError(editText, textInputLayout, errorMsg);
            return true;
        } else
            return false;
    }

    public boolean isContainSpace(EditText editText, TextInputLayout textInputLayout, String errorMsg) {
        if (editText.getText().toString().contains(" ")) {
            setError(editText, textInputLayout, errorMsg);
            return false;
        } else
            return true;
    }


    public void setError(EditText editText, TextInputLayout inputLayout, String errorMessage) {
        try {
            if (!TextUtils.isEmpty(editText.getText()))
                editText.requestFocus(editText.getText().toString().length());
            else
                editText.requestFocus();
            inputLayout.setError(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkPattren(EditText editText, TextInputLayout textInputLayout, String errorMsg) {
        boolean isPattern = false;
        String edit_text_name = editText.getText().toString();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(edit_text_name).find()) {
            PrintLog.d("Utility", "SPECIAL CHARS FOUND");
            setError(editText, textInputLayout, errorMsg);
            isPattern = true;
        }
        return isPattern;
    }


    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validateAlphaNumericPassword(final String password) {
        /*String PASSWORD_PATTERN =
				"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";*/
        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%&*()_+=|:;<>?{}\\[\\]~-]).{6,15})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
