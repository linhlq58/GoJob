package com.freshvegetable.gojob.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.PostAdapter;
import com.freshvegetable.gojob.models.Post;

import java.util.ArrayList;

/**
 * Created by Nam on 8/15/2016.
 */
public class NewestFragment extends Fragment {
    ListView newestPortList;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> posts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragmant_newest, container, false);
        newestPortList = (ListView) rootView.findViewById(R.id.newestPortList);
        initPostList();
        mPostAdapter = new PostAdapter(this.getContext(), posts);
        newestPortList.setAdapter(mPostAdapter);
        Log.d("post: ", String.valueOf(mPostAdapter.getCount()));
        Log.d("post: ", String.valueOf(newestPortList.getChildCount()));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initPostList() {
        posts = new ArrayList<>();
        posts.add(new Post("Meo_3_the", System.currentTimeMillis(), "blahh", "Ai ve dc con meo nay k", new int[]{R.drawable.mirana}));
        posts.add(new Post("Meo_3_the", System.currentTimeMillis(), "Chan doi", ":(((((((", new int[]{R.drawable.mirana}));
    }
}
