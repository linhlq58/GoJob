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
        categoryListView.setItemAnimator(new DefaultItemAnimator());;

        super.onViewCreated(view, savedInstanceState);

    }


    private void initList() {
        categories = new ArrayList<>();
        categories.add(new Category("57b95bd0171a74810a11ad40", R.mipmap.web, "Web", 1003));
        categories.add(new Category("57b95c54171a74810a11ad41", R.mipmap.design, "Design", 1003));
        categories.add(new Category("57b95c63171a74810a11ad42", R.mipmap.mobile, "Mobile", 1003));
        categories.add(new Category("57b95c73171a74810a11ad43", R.mipmap.tester, "Tester", 1003));
        categories.add(new Category("57b95c7a171a74810a11ad44", R.mipmap.data, "Database", 1003));
    }
}
