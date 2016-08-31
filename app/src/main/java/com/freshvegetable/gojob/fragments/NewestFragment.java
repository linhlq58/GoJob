package com.freshvegetable.gojob.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.PostAdapter;
import com.freshvegetable.gojob.models.Post;
import com.freshvegetable.gojob.models.User;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.Utils;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nam on 8/15/2016.
 *
 */
public class NewestFragment extends Fragment {
    @BindView(R.id.newestPostList)
    RecyclerView newestPostList;
    @BindView(R.id.postListRefresher)
    SwipeRefreshLayout postListRefresher;
    private ArrayList<Post> posts = new ArrayList<>();

    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.zzz'Z'", Locale.ENGLISH);
//    private SimpleDateFormat strToDateFormat = new SimpleDateFormat("yyyy-mm-ddThh:MM:ss.298Z");

    private PostAdapter mPostAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragmant_newest, container, false);
        ButterKnife.bind(this, rootView);

        setUpNetworkConnection();
        mPostAdapter = new PostAdapter(this.getContext(), posts, mImageLoader);
        if (Utils.isNetworkConnected(this.getContext())) {
            setupView();
            mPostAdapter.notifyDataSetChanged();
            postListRefresher.setColorSchemeResources(R.color.colorPrimary);
            postListRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getPostFromServer();
                    mPostAdapter.notifyDataSetChanged();
                    postListRefresher.setRefreshing(false);
                }
            });

        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getPostFromServer() {
        posts.clear();
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
                                String name = user.getString(VolleyRequest.DISPLAY_NAME);

                                String created = mJSONObject.getString(VolleyRequest.CREATED);
                                Date mDate = null;
                                try {
                                    mDate = mDateFormat.parse(created);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String title = mJSONObject.getString(VolleyRequest.TITLE);
                                String content = mJSONObject.getString(VolleyRequest.POST_CONTENT);


                                String image = mJSONObject.getString(VolleyRequest.POST_IMAGE_URL);
                                Post post = new Post(new User.UserHolder(id, name, null, null),
                                        mDate == null ? null : mDate.getTime(),
                                        title, content, image);
                                posts.add(post);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mPostAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.toString());
                    }
                }
        );
        mQueue.add(postRequest);


    }

//    private User.UserHolder getUserData(final String id, final VolleyCallback callback) {
//        String url = Url.BASE_URL + Url.GET_USER_DETAIL_URL + id;
//
//        JsonObjectRequest userRequest = new JsonObjectRequest(Request.Method.GET, url,
//                new JSONObject(),
//                new Response.Listener<JSONObject>() {
//                    User.UserHolder userHolder;
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        callback.onSuccess(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Error:", error.toString());
//                    }
//                }
//        );
//        mQueue.add(userRequest);
//        return null;
//    }
//
//    private User.UserHolder parseUser(JSONObject response) {
//        try {
//            String id = response.getString(VolleyRequest.ID);
//            String displayName = response.getString(VolleyRequest.DISPLAY_NAME);
//            String username = response.getString(VolleyRequest.USERNAME);
//            String profileImage = response.getString(VolleyRequest.PROFILE_IMAGE_URL);
//            return new User.UserHolder(id, displayName, username, profileImage);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void setUpNetworkConnection() {
        mQueue = Volley.newRequestQueue(this.getContext());
        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private void setupView() {
        newestPostList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        newestPostList.setLayoutManager(mLayoutManager);

        getPostFromServer();
        newestPostList.setItemAnimator(new DefaultItemAnimator());
        newestPostList.setAdapter(mPostAdapter);
    }
}
