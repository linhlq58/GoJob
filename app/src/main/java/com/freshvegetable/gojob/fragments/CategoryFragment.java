package com.freshvegetable.gojob.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.CategoryAdapter;
import com.freshvegetable.gojob.models.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/15/2016.
 */
public class CategoryFragment extends Fragment {

    @BindView(R.id.categoryListView)
    RecyclerView categoryListView;

    private CategoryAdapter mCategoryAdapter;
    private ArrayList<Category> categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        categoryListView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        categoryListView.setLayoutManager(mLayoutManager);
        initList();
        mCategoryAdapter = new CategoryAdapter(categories);
        categoryListView.setAdapter(mCategoryAdapter);
        categoryListView.setItemAnimator(new DefaultItemAnimator());
        Log.d("count", categoryListView.getChildCount() + "");
        super.onViewCreated(view, savedInstanceState);
    }

    private void initList() {
        categories = new ArrayList<>();
        categories.add(new Category(R.mipmap.web, "Web", 1003));
        categories.add(new Category(R.mipmap.design, "Design", 1003));
        categories.add(new Category(R.mipmap.mobile, "Mobile", 1003));
        categories.add(new Category(R.mipmap.tester, "Tester", 1003));
        categories.add(new Category(R.mipmap.data, "Database", 1003));
    }
}
