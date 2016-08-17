package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.models.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NamVp on 17/08/2016.
 */
public class PostAdapter extends BaseAdapter {
    private Context mContext;
    private final int layoutId = R.layout.item_list_post;
    private ArrayList<Post> posts;

    public PostAdapter(Context mContext, ArrayList<Post> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderPost holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
            holder = new ViewHolderPost(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolderPost) view.getTag();
        }

        Post post = posts.get(i);

        holder.tvUsername.setText(post.getUser());
        if (post.getContent().equals("")) holder.tvPostContent.setVisibility(View.GONE);
        else holder.tvPostContent.setText(post.getContent());
        if (post.getImgUrl().length == 0) holder.imgPost.setVisibility(View.GONE);
        else
            holder.imgPost.setImageDrawable(ContextCompat.getDrawable(mContext, post.getImgUrl()[0]));
        return view;
    }

    public static class ViewHolderPost {
        private Context mContext;

        @BindView(R.id.userName)
        TextView tvUsername;
        @BindView(R.id.tvCreateTime)
        TextView tvCreateTime;
        @BindView(R.id.tvPostContent)
        TextView tvPostContent;
        @BindView(R.id.imgPost)
        ImageView imgPost;

        public ViewHolderPost(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
