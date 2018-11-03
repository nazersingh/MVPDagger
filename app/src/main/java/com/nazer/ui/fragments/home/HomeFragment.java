package com.nazer.ui.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nazer.R;
import com.nazer.ui.base.BaseFragment;
import com.nazer.util.PrintLog;

public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isOptionMenu(false);
        setUpLayout();
        setUserVisibleHint(true);
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void setUpLayout() {

    }

    @Override
    public void setUpView() {

    }

    @Override
    public boolean getUserVisibleHint() {
        PrintLog.e(" Home Fragment"," "+super.getUserVisibleHint());
        return super.getUserVisibleHint();
    }
}
