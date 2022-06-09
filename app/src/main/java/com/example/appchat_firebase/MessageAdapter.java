package com.example.appchat_firebase;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<MessageOj> mListMessage;

    public void setData(List<MessageOj> list){
        this.mListMessage = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_massage,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageOj message = mListMessage.get(position);
        if(message == null){
            return;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams. MATCH_PARENT ,
                LinearLayout.LayoutParams. WRAP_CONTENT ) ;
        // Thử tin nhắn 2 bên
        if(message.getMessage().equals("1")){
            layoutParams.setMargins(0, 0, 200, 0);
            holder.layoutMessage.setLayoutParams(layoutParams);
            holder.layoutMessage.setGravity(Gravity.START);

            holder.tvMassage.setBackgroundResource(R.drawable.bg_blue_conner_left);
            holder.tvMassage.setTextColor(0xFF000000);
            holder.tvMassage.setText(message.getMessage());
        }else{
            layoutParams.setMargins(200, 0, 0, 0);
            holder.layoutMessage.setLayoutParams(layoutParams);
            holder.layoutMessage.setGravity(Gravity.END);

            holder.tvMassage.setBackgroundResource(R.drawable.bg_blue_conner);
            holder.tvMassage.setTextColor(0xFFFFFFFF);
            holder.tvMassage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if(mListMessage != null){
            return mListMessage.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMassage;
        private LinearLayout layoutMessage;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMassage = itemView.findViewById(R.id.tv_message);
            layoutMessage = itemView.findViewById(R.id.layout_message);
        }
    }
}
