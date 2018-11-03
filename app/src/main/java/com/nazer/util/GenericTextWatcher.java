package com.nazer.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {

    private static GenericTextWatcher genericTextWatcher;
    public EditText edt_1;
    public EditText edt_2;
    public EditText edt_3;
    public EditText edt_4;
    boolean isTrue = false;

    private GenericTextWatcher(EditText view, EditText edt_2, EditText edt_3, EditText edt_4) {
        this.edt_1 = view;
        this.edt_2 = edt_2;
        this.edt_3 = edt_3;
        this.edt_4 = edt_4;
    }

    public static GenericTextWatcher getInstance(EditText view, EditText edt_2, EditText edt_3, EditText edt_4) {
//        if(genericTextWatcher==null)
        genericTextWatcher = new GenericTextWatcher(view, edt_2, edt_3, edt_4);

        return genericTextWatcher;
    }


    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();

//        if (edt_4.getText().toString().length()==0) {
//            edt_3.requestFocus();
//        } else if (edt_3.toString().isEmpty() && edt_3.toString().equals("")) {
//
//            edt_2.requestFocus();
//        } else if (edt_2.toString().isEmpty() && edt_2.toString().equals("")) {
//
//            edt_1.requestFocus();
//        }


//        switch (view.getId()) {
//
//            case R.id.edt_verify_1:
//                if (text.length() == 1) {
//                    edt_2.requestFocus();
//
//                }
//                break;
//            case R.id.edt_verify_2:
//                if (text.length() == 1) {
//                    edt_3.requestFocus();
//                }
//                break;
//            case R.id.edt_verify_3:
//                if (text.length() == 1) {
//                    edt_4.requestFocus();
//                }
//
//                break;
//            case R.id.edt_verify_4:
//                break;
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        if (edt_1.getText().toString().length() > 0) {
            edt_2.requestFocus();


        }
        if (edt_2.getText().toString().length() > 0) {
            edt_3.requestFocus();

        }
        if (edt_3.getText().toString().length() > 0) {
            edt_4.requestFocus();

        }

    }
}
