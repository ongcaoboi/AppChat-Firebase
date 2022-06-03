package com.example.appchat_firebase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatMainAdapter extends ArrayAdapter<UserOj> {
    private Context context;
    private int resource;
    private List<UserOj> ulist;

    public ChatMainAdapter(Context context, int resource, List<UserOj> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.ulist=objects;
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserOj user = ulist.get(position);
        viewHolder.imgAvatar.setBackgroundResource(R.drawable.a1);
        viewHolder.tvName.setText(user.getFirstName().toString()+" "+user.getLastName().toString());

        String status = "tin nhắn mới";
        viewHolder.tvLastestMessage.setTextColor(Color.parseColor("#444444"));
        viewHolder.tvLastestMessage.setText(status);

        return convertView;
    }


    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvLastestMessage;
    }
}
