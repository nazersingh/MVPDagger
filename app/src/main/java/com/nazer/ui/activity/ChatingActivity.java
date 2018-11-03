package com.nazer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;


import com.nazer.R;
import com.nazer.pojomodels.ChatModel;
import com.nazer.ui.adapter.ChatScreenAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatingActivity extends AppCompatActivity {

    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.edt_chat_snd)
    EditText mEdtChatSnd;
    @BindView(R.id.iv_snd_msg)
    CircleImageView mIvSndMsg;
    ChatModel mModel;
    List<ChatModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chat_screen);
        ButterKnife.bind(this);

        mRvChat.setHasFixedSize(true);
        mModel = new ChatModel();
        mList = new ArrayList<>();

        mModel.setMType(0);
        mModel.setMessage("Hello there how are you?");
        mModel.setSndTime("10:00pm");
//        mList.add(mModel);
//        mModel.setMType(2);
//        mModel.setMessage("Hi i am fine,what about u?");
//        mModel.setRecTime("10:01pm");
        mList.add(mModel);
        for (int i = 0; i < 10; i++) {


        }


        mRvChat.setLayoutManager(new LinearLayoutManager(this));
        mRvChat.setAdapter(new ChatScreenAdapter(mList, this));
    }
}
