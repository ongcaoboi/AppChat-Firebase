package com.example.appchat_firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvMassage.setText(message.getMessage());
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
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMassage = itemView.findViewById(R.id.tv_message);
        }
    }
}
