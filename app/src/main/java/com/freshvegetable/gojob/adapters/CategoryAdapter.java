package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freshvegetable.gojob.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/10/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private final int layoutId = R.layout.item_list_category;
    private int[] icons = {R.mipmap.web, R.mipmap.mobile, R.mipmap.design, R.mipmap.tester, R.mipmap.data};
    private String[] titles = {"Web", "Mobile", "Design", "Tester", "Database"};
    private int[] counts = {1000, 1900, 1570, 1234, 2331};
    private int[] backgrounds = {
            Color.parseColor("#f6b398"),
            Color.parseColor("#f67280"),
            Color.parseColor("#c06c84"),
            Color.parseColor("#6c5b7b"),
            Color.parseColor("#355c7d"),
    };

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.categoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, icons[i]));
        holder.categoryTitle.setText(titles[i]);
        holder.postCount.setText(counts[i] + " posts");
        view.setBackgroundColor(backgrounds[i]);
        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.imgCategoryIcon)
        ImageView categoryIcon;
        @BindView(R.id.categoryTitle)
        TextView categoryTitle;
        @BindView(R.id.categoryPostCount)
        TextView postCount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
