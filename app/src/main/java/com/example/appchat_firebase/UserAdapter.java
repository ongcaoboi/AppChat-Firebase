package com.example.appchat_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appchat_firebase.R;
import com.example.appchat_firebase.UserOj;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);

            viewHolder.tvTrangThai =
                    (TextView) convertView.findViewById(R.id.condition_contact);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.image_avatar_contact);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserOj user = ulist.get(position);
        viewHolder.imgAvatar.setBackgroundResource(R.drawable.a1);
        Toast.makeText( getContext(), user.getFirstName(), Toast.LENGTH_SHORT).show();
        return convertView;
/* Không set được ảnh
  không set được text
  \
*/
//        viewHolder.tvName.setText("");
////        viewHolder.tvName.setText(user.getFirstName().toString()+" "+user.getLastName().toString());
//        String th = "Đang hoạt động";
//        if(user.isTrangThai() != true)
//            th = "Offline";
//        viewHolder.tvTrangThai.setText(th);
//        return convertView;

//        Object viewHolder;
//        if(convertView == null){
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false);
//            ((ViewHolder) viewHolder).imgAvatar = (ImageView) convertView.findViewById(R.id.image_avatar);
//            ((ViewHolder) viewHolder).tvName = (TextView) convertView.findViewById(R.id.tv_name);
//            ((ViewHolder) viewHolder).tvTrangThai = (TextView) convertView.findViewById(R.id.condition_contact);
//
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        UserOj user = ulist.get(position);
//        ((ViewHolder) viewHolder).tvName.setText(user.getFirstName()+" "+user.getLastName());
//
//
//        if(user.isTrangThai()==true){
//            ((ViewHolder) viewHolder).tvTrangThai.setText("Đang hoạt động");
//        }else {
//            ((ViewHolder) viewHolder).tvTrangThai.setText("Rời máy");
//        }
//
//        ((ViewHolder) viewHolder).imgAvatar.setBackgroundResource(R.drawable.avatar);
//
//        return super.getView(position, convertView, parent);
    }


    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvTrangThai;
    }

}
