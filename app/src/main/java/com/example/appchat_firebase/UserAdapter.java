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

public class UserAdapter extends ArrayAdapter<UserOj> {
    private Context context;
    private int resource;
    private List<UserOj> ulist;

    public UserAdapter(Context context, int resource, List<UserOj> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.ulist=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name_contact);
            viewHolder.tvTrangThai = (TextView) convertView.findViewById(R.id.condition_contact);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.image_avatar_contact);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserOj user = ulist.get(position);
        viewHolder.imgAvatar.setBackgroundResource(R.drawable.avatar);
        viewHolder.tvName.setText(user.getFirstName().toString()+" "+user.getLastName().toString());
        String status = "";
        if(user.isTrangThai()){
            status = "Đang hoạt động";
            viewHolder.tvTrangThai.setTextColor(Color.parseColor("#10e53e"));
        }else {
            status = "Rời máy";
            viewHolder.tvTrangThai.setTextColor(Color.parseColor("#818181"));
        }
        viewHolder.tvTrangThai.setText(status);

        return convertView;
    }


    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvTrangThai;
    }

}
