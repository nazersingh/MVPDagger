package com.nazer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nazer.R;
import com.nazer.pojomodels.ChatInboxModel;
import com.nazer.ui.activity.ChatingActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatInboxAdapter extends RecyclerView.Adapter<ChatInboxAdapter.ViewHolder> {

    List<ChatInboxModel> mList;
    Context mCOntext;

    public ChatInboxAdapter(List<ChatInboxModel> mList, Context mCOntext) {
        this.mList = mList;
        this.mCOntext = mCOntext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_inbox_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTvLstMsg.setText(mList.get(i).getLastMessage());
        viewHolder.mTvUsernameInbox.setText(mList.get(i).getSenderName());
        viewHolder.mTvLstMsgTym.setText(mList.get(i).getLastTime());
        viewHolder.mRelinbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mCOntext,ChatingActivity.class);
                mCOntext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mIvUserInbox;
        TextView mTvUsernameInbox;
        TextView mTvLstMsgTym;
        TextView mTvLstMsg;
        RelativeLayout mRelinbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelinbox = itemView.findViewById(R.id.rel_inbox);
            mTvUsernameInbox = itemView.findViewById(R.id.tv_usr_name_inbox);
            mIvUserInbox = itemView.findViewById(R.id.iv_user_inbox);
            mTvLstMsg = itemView.findViewById(R.id.tv_lst_msg);
            mTvLstMsgTym = itemView.findViewById(R.id.tv_lst_msg_tym);


        }
    }
}
