package com.freshvegetable.gojob.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.freshvegetable.gojob.models.*;

import com.android.volley.toolbox.ImageLoader;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.widgets.CircleImage;

import java.util.List;

/**
 * Created by duyti on 8/25/2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;
    private ImageLoader mImageLoader;

    public MessageAdapter(List<Message> messages, ImageLoader imageLoader) {
        mMessages = messages;
        mImageLoader = imageLoader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Message.TYPE_MESSAGE_OTHER:
                layout = R.layout.item_message_other;
                break;
            case Message.TYPE_MESSAGE:
                layout = R.layout.item_message;
                break;
            default:
                break;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        viewHolder.setMessage(message.getmMessage());
        viewHolder.setUsername(message.getmUsername());
        viewHolder.setTime(message.getmTime());
        if (message.getmAvatarURL().equals(""))
            viewHolder.mAvatar.setVisibility(View.GONE);
        else {
            mImageLoader.get(message.getmAvatarURL(),
                    ImageLoader.getImageListener(viewHolder.mAvatar, R.mipmap.user, R.mipmap.user));
        }
//        if(position>0 && mMessages.get(position-1).getmUsername().equals(message.getmUsername())){
//            viewHolder.mAvatar.setVisibility(View.INVISIBLE);
//            viewHolder.mTime.setVisibility(View.GONE);
//            viewHolder.mUsernameView.setVisibility(View.GONE);
//        }else{
//            viewHolder.mAvatar.setVisibility(View.VISIBLE);
//            viewHolder.mTime.setVisibility(View.VISIBLE);
//            viewHolder.mUsernameView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getmType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUsernameView;
        TextView mMessageView;
        CircleImage mAvatar;
        TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsernameView = (TextView) itemView.findViewById(R.id.username);
            mMessageView = (TextView) itemView.findViewById(R.id.message);
            mAvatar = (CircleImage) itemView.findViewById(R.id.iv_avatar);
            mTime = (TextView)itemView.findViewById(R.id.tv_time);
        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }

        public void setTime(String time){
            if(mTime== null) return;
            mTime.setText(time);
        }
    }
}
