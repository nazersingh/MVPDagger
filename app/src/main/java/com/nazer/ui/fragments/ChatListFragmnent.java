package com.nazer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.nazer.R;
import com.nazer.pojomodels.ChatInboxModel;
import com.nazer.ui.adapter.ChatInboxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatListFragmnent extends Fragment {

    @BindView(R.id.rv_inbox)
    RecyclerView mRvInbox;
    Unbinder unbinder;
    List<ChatInboxModel> mList;

    ChatInboxModel mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_chat_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRvInbox.setHasFixedSize(true);
        mModel = new ChatInboxModel();

        mList = new ArrayList<ChatInboxModel>();
//        mModel.setSenderName("Champ");
//        mModel.setLastMessage("Hello, How are you???");
//        mModel.setLastTime("1:23pm");
        for (int i = 0; i < 10; i++) {
            mList.add(mModel);
        }

        mRvInbox.setAdapter(new ChatInboxAdapter(mList, getActivity()));
        mRvInbox.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
