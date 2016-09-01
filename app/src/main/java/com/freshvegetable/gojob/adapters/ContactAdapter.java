package com.freshvegetable.gojob.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.models.Contact;
import com.freshvegetable.gojob.widgets.CircleImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by duyti on 8/21/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> mContacts;
    private ImageLoader mImageLoader;
    private OnClickItemView listener;

    public void setOnClickItemView(OnClickItemView event){
        listener = event;
    }

    public ContactAdapter(List<Contact> contacts,ImageLoader imageLoader) {
        mContacts = contacts;
        mImageLoader = imageLoader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Contact contact = mContacts.get(position);
        viewHolder.setDisPlayName(contact.getDisplayName());
        viewHolder.setCreated(contact.getCreated());
        viewHolder.setUsername(contact.getUsername());
        if (contact.getAvatarURL().equals(""))
            viewHolder.mAvatar.setVisibility(View.GONE);
        else {
            mImageLoader.get(contact.getAvatarURL(),
                    ImageLoader.getImageListener(viewHolder.mAvatar, R.mipmap.user, R.mipmap.iv_bg));
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImage mAvatar;
        TextView disPlayName, created, username;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.userName);
            mAvatar = (CircleImage) itemView.findViewById(R.id.avatar);
            disPlayName = (TextView) itemView.findViewById(R.id.displayName);
            created = (TextView) itemView.findViewById(R.id.created);
            itemView.setOnClickListener(this);
        }

        public void setDisPlayName(String displayName){
            this.disPlayName.setText(displayName);
        }
        public void setUsername(String username){
            this.username.setText(username);
        }

        public void setCreated(String time){
            created.setText(time);
        }

        @Override
        public void onClick(View view) {
            listener.onClickItem(getAdapterPosition());
        }
    }

    public interface OnClickItemView{
        void onClickItem(int position);
    }

}
