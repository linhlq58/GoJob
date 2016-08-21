package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.models.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/10/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategory> {
    private final int layoutId = R.layout.item_list_category;
    private ArrayList<Category> categories;
    private int[] backgrounds = {
            Color.parseColor("#f6b398"),
            Color.parseColor("#f67280"),
            Color.parseColor("#c06c84"),
            Color.parseColor("#6c5b7b"),
            Color.parseColor("#355c7d"),
    };

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ViewHolderCategory(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCategory holder, int position) {
        final Category category = categories.get(position);
        holder.categoryIcon.setImageDrawable(ContextCompat.getDrawable(holder.mContext, category.getCategoryImage()));
        holder.categoryTitle.setText(category.getCategoryTitle());
        holder.postCount.setText(String.valueOf(category.getPostCount()) + "posts");
        holder.container.setBackgroundColor(backgrounds[position]);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(holder.container, category.getCategoryTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolderCategory extends RecyclerView.ViewHolder {
        @BindView(R.id.imgCategoryIcon)
        ImageView categoryIcon;
        @BindView(R.id.categoryTitle)
        TextView categoryTitle;
        @BindView(R.id.categoryPostCount)
        TextView postCount;
        @BindView(R.id.container)
        CardView container;

        Context mContext = null;

        public ViewHolderCategory(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mContext = view.getContext();
        }
    }
}
