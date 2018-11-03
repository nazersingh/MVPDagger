package com.nazer.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nazer.R;
import com.nazer.pojomodels.ChatModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ChatModel> mList;
    Context mContext;

    public ChatScreenAdapter(List<ChatModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_send, viewGroup, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_receive, viewGroup, false);
            return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder.getItemViewType() == 0) {
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            viewHolder1.mTvMsgSend.setText(mList.get(i).getMessage());
            viewHolder1.mTvMsgTym.setText(mList.get(i).getSndTime());

        } else {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.mTvMsgRec.setText(mList.get(i).getMessage());
            myViewHolder.mTvMsgTym.setText(mList.get(i).getRecTime());
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getMType() == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mIvUserSendr;
        TextView mTvMsgRec, mTvMsgTym;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvUserSendr = itemView.findViewById(R.id.iv_user_sendr);
            mTvMsgRec = itemView.findViewById(R.id.tv_msg_rec);
            mTvMsgTym = itemView.findViewById(R.id.tv_cht_rec_tym);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTvMsgSend, mTvMsgTym;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvMsgSend = itemView.findViewById(R.id.tv_msg_send);
            mTvMsgTym = itemView.findViewById(R.id.tv_msg_tym);
        }
    }
}
