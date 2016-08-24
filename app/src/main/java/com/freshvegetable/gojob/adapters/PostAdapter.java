package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.models.Post;
import com.freshvegetable.gojob.utils.Url;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NamVp on 17/08/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolderPost> {
    private Context mContext;
    private final int layoutId = R.layout.item_list_post;
    private ArrayList<Post> posts;
    private ImageLoader imageLoader;


    public PostAdapter(Context mContext, ArrayList<Post> posts, ImageLoader imageLoader) {
        this.mContext = mContext;
        this.posts = posts;
        this.imageLoader = imageLoader;
    }

    public static class ViewHolderPost extends RecyclerView.ViewHolder {
        private Context mContext;

        @BindView(R.id.userName)
        TextView tvUsername;
        @BindView(R.id.tvCreateTime)
        TextView tvCreateTime;
        @BindView(R.id.tvPostContent)
        TextView tvPostContent;
        @BindView(R.id.imgPost)
        ImageView imgPost;
        @BindView(R.id.divider)
        LinearLayout divider;

        public ViewHolderPost(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolderPost onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_post, parent, false);
        return new ViewHolderPost(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderPost holder, int position) {
        Post post = posts.get(position);
        holder.tvUsername.setText(post.getUser().getDisplayName());
//        holder.tvCreateTime.setText();
        holder.tvPostContent.setText(post.getContent());
        Log.d("img", post.getImgUrl());
        if (post.getImgUrl().equals("")) holder.imgPost.setVisibility(View.GONE);
        else {
//        holder.imgPost.setImageDrawable(ContextCompat.getDrawable(mContext, post.getImgUrl()[0]));
            imageLoader.get(Url.BASE_URL + post.getImgUrl().substring(1),
                    ImageLoader.getImageListener(holder.imgPost, R.drawable.mirana, R.mipmap.iv_bg));
        }
        if (position == posts.size() - 1) holder.divider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
