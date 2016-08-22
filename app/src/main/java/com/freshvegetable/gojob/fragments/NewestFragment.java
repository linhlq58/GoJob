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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.PostAdapter;
import com.freshvegetable.gojob.models.Post;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/15/2016.
 */
public class NewestFragment extends Fragment {
    @BindView(R.id.newestPortList)
    RecyclerView newestPortList;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> posts;

    private RequestQueue mQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(this.getContext());
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragmant_newest, container, false);
        ButterKnife.bind(this, rootView);
        getPostFromServer();
        mPostAdapter = new PostAdapter(this.getContext(), posts);
        newestPortList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        newestPortList.setLayoutManager(mLayoutManager);
        newestPortList.setItemAnimator(new DefaultItemAnimator());
        newestPortList.setAdapter(mPostAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getPostFromServer() {
        posts = new ArrayList<>();
        String url = Url.BASE_URL + Url.POST_API_URL;
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject mJSONObject = response.getJSONObject(i);
                                JSONObject user = mJSONObject.getJSONObject("user");
                                String id = user.getString(VolleyRequest.ID);
                                getUserData(id);
                                String title = mJSONObject.getString(VolleyRequest.TITLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.toString());
                    }
                });
        mQueue.add(postRequest);
    }

    private void getUserData(String id) {
        String url = Url.BASE_URL + Url.GET_USER_DETAIL_URL + id;

        JsonObjectRequest userRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("User", response.getString(VolleyRequest.USERNAME));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.toString());
                    }
                }
        );
    }
}
