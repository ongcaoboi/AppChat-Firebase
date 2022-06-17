package com.example.appchat_firebase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchat_firebase.services.ChatMainTmp;
import com.example.appchat_firebase.services.Global;

import java.util.List;

public class ChatMainAdapter extends ArrayAdapter<ChatMainTmp> {
    private Context context;
    private int resource;
    private List<ChatMainTmp> chatMainTmp;

    public ChatMainAdapter(Context context, int resource, List<ChatMainTmp> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.chatMainTmp=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chatpage, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvLastestMessage = (TextView) convertView.findViewById(R.id.tv_lastest_message);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.image_avatar);
            viewHolder.dotStatus = (ImageView) convertView.findViewById(R.id.dot_status);
            viewHolder.dotWatched = (ImageView) convertView.findViewById(R.id.dot_watched);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(!chatMainTmp.isEmpty()){
            viewHolder.dotWatched.setVisibility(View.INVISIBLE);
            UserOj user = chatMainTmp.get(position).getUser();
            if(user.isGioiTinh()){
                viewHolder.imgAvatar.setBackgroundResource(R.drawable.male);
            }else {
                viewHolder.imgAvatar.setBackgroundResource(R.drawable.female);
            }
            viewHolder.tvName.setText(user.getFirstName().toString()+" "+user.getLastName().toString());
            String newMessage = "";
            viewHolder.tvLastestMessage.setTextColor(Color.parseColor("#818181"));
            if(chatMainTmp.get(position).getChatInfo().getMessageNew().getUserId().equals(Global.user.getId())){
                newMessage = "Bạn: "+chatMainTmp.get(position).getChatInfo().getMessageNew().getMsg();
            }else{
                newMessage =chatMainTmp.get(position).getUser().getLastName()+": "+chatMainTmp.get(position).getChatInfo().getMessageNew().getMsg();
                if(chatMainTmp.get(position).getChatInfo().getMessageNew().getStatus() == 0) {
                    viewHolder.tvLastestMessage.setTextColor(Color.parseColor("#0099FF"));
                    viewHolder.dotWatched.setVisibility(View.VISIBLE);
                }
            }
            String value = newMessage;
            if(newMessage.length() > 35){
                value = newMessage.substring(0,31)+"...";
            }
            viewHolder.tvLastestMessage.setText(value);
            if(user.isTrangThai()){
                viewHolder.dotStatus.setBackgroundResource(R.drawable.dot_online);
            }else{
                viewHolder.dotStatus.setBackgroundResource(R.drawable.dot_offline);
            }
        }

        return convertView;
    }


    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvLastestMessage;
        ImageView dotStatus;
        ImageView dotWatched;
    }
}
