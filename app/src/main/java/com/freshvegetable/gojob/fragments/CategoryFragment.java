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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/15/2016.
 */
public class CategoryFragment extends Fragment {

    @BindView(R.id.categoryListView)
    RecyclerView categoryListView;

    private CategoryAdapter mCategoryAdapter;

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

        mCategoryAdapter = new CategoryAdapter(this.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        categoryListView.setLayoutManager(mLayoutManager);
        categoryListView.setAdapter(mCategoryAdapter);
        categoryListView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

}
