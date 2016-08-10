package com.freshvegetable.gojob.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.freshvegetable.gojob.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/10/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private final int layoutId = R.layout.item_list_category;
    private String[] titles = {"Web", "Mobile", "Design", "Tester"};
    private int[] counts = {1000, 1900, 1570, 1234 };
    private int[] backgrounds = {
            Color.parseColor("#d3e68d"),
            Color.parseColor("#a7d189"),
            Color.parseColor("#539799"),
            Color.parseColor("#485d70"),
            Color.parseColor("#c7e271"),
            Color.parseColor("#93c36d"),
            Color.parseColor("#297d7d"),
            Color.parseColor("#1c3450"),
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
        holder.catagoryTitle.setText(titles[i]);
        holder.postCount.setText(counts[i] + " posts");
        view.setBackgroundColor(backgrounds[i]);
        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.categoryTitle)
        TextView catagoryTitle;
        @BindView(R.id.categoryPostCount)
        TextView postCount;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
