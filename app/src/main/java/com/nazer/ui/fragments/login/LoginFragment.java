package com.nazer.ui.fragments.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nazer.R;
import com.nazer.pojomodels.ResponsePojo;
import com.nazer.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment {

    LoginPresenter loginPresenter;

    @BindView(R.id.btn_click)
    Button mBtnClick;
    Unbinder unbinder;

    String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isOptionMenu(true);
        setOptionMenu(R.menu.home);
        setUpLayout();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setUpLayout() {
        loginPresenter = new LoginPresenter(loginPresenterCallBack);
    }

    @Override
    public void setUpView() {

    }

    /**
     * =========== LoginPresenter Callback
     */
    LoginPresenter.LoginPresenterCallBack loginPresenterCallBack = new LoginPresenter.LoginPresenterCallBack() {
        @Override
        public void onSuccess(ResponsePojo response) {

            if (response.getStatusCode() == 200) {
                //update Ui
            } else
                checkApiStatusCode(response.getStatusCode(), response.getMessage());

        }

        @Override
        public void onFailure(Throwable throwable) {
            checkApiFailure(throwable);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        //working
//        loginPresenter.callLogin(new HashMap());
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                addFragment(new HomeFragment());
//            }
//        }, 3000);

//        showImagePicker();
//        showVideoPicker();
//        getDatePicker().showDatePicker(getActivity(), new Dialogues.DialogueOneButtonCallback() {
//            @Override
//            public void OnOkClick(Object o) {
//
//            }
//        });

        //error comming
//        PrintLog.e(TAG, "" + getLocation());
//        PrintLog.e(TAG, "" + getAddressFromLocation(getLocation()));

    }
}
