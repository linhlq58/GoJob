package com.freshvegetable.gojob.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        super.onViewCreated(view, savedInstanceState);

    }


    private void initList() {
        categories = new ArrayList<>();
        categories.add(new Category("57be5f0154f5a50e0050ca12", R.mipmap.web, "Web", 1003));
        categories.add(new Category("57be5f2054f5a50e0050ca14", R.mipmap.design, "Design", 1003));
        categories.add(new Category("57be5f1254f5a50e0050ca13", R.mipmap.mobile, "Mobile", 1003));
        categories.add(new Category("57be5f2954f5a50e0050ca15", R.mipmap.tester, "Tester", 1003));
        categories.add(new Category("57be5f3354f5a50e0050ca16", R.mipmap.data, "Database", 1003));
    }
}
