package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freshvegetable.gojob.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/10/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context mContext;
    private final int layoutId = R.layout.item_list_category;
    private int[] icons = {
            R.mipmap.web,
            R.mipmap.mobile,
            R.mipmap.design,
            R.mipmap.tester,
            R.mipmap.data};
    private String[] titles = {"Web", "Mobile", "Design", "Tester", "Database"};
    private int[] counts = {1000, 1900, 1570, 1234, 2331};
    private int[] backgrounds = {
            Color.parseColor("#f6b398"),
            Color.parseColor("#f67280"),
            Color.parseColor("#c06c84"),
            Color.parseColor("#6c5b7b"),
            Color.parseColor("#355c7d"),
    };

    public CategoryAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.categoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, icons[position]));
        holder.categoryTitle.setText(titles[position]);
        holder.postCount.setText(counts[position] + " posts");
        holder.container.setBackgroundColor(backgrounds[position]);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.imgCategoryIcon)
        ImageView categoryIcon;
        @BindView(R.id.categoryTitle)
        TextView categoryTitle;
        @BindView(R.id.categoryPostCount)
        TextView postCount;
        @BindView(R.id.container)
        LinearLayout container;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
