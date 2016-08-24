package com.freshvegetable.gojob.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.PostAdapter;
import com.freshvegetable.gojob.models.Post;
import com.freshvegetable.gojob.models.User;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.VolleyCallback;
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
    private ArrayList<Post> posts;

    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
//    private SimpleDateFormat strToDateFormat = new SimpleDateFormat("yyyy-mm-ddThh:MM:ss.298Z");

    private User.UserHolder userHolder;

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


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        newestPortList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) newestPortList.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        newestPortList.setLayoutParams(params);
        newestPortList.setLayoutManager(mLayoutManager);

        getPostFromServer();
        PostAdapter mPostAdapter = new PostAdapter(this.getContext(), posts, mImageLoader);
        newestPortList.setItemAnimator(new DefaultItemAnimator());
        newestPortList.setAdapter(mPostAdapter);

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
                                String name = user.getString(VolleyRequest.DISPLAY_NAME);


                                String createTime = mJSONObject.getString(VolleyRequest.CREATED);
//                                long date = strToDateFormat.parse(createTime).getTime();

//                                String title = mJSONObject.getString(VolleyRequest.TITLE);

                                String content = mJSONObject.getString(VolleyRequest.POST_CONTENT);

                                String image = mJSONObject.getString(VolleyRequest.POST_IMAGE_URL);
                                userHolder = getUserData(id, new VolleyCallback() {
                                    @Override
                                    public void onSuccess(JSONObject response) {
                                        parceUser(response);
                                    }
                                });
                                Post post = new Post(new User.UserHolder(id, name, null, null), null, null, content, image);
                                posts.add(post);
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

    private User.UserHolder getUserData(final String id, final VolleyCallback callback) {
        String url = Url.BASE_URL + Url.GET_USER_DETAIL_URL + id;

        JsonObjectRequest userRequest = new JsonObjectRequest(Request.Method.GET, url,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    User.UserHolder userHolder;

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.toString());
                    }
                }
        );
        mQueue.add(userRequest);
        return null;
    }

    private User.UserHolder parceUser(JSONObject response) {
        try {
            String id = response.getString(VolleyRequest.ID);
            String displayName = response.getString(VolleyRequest.DISPLAY_NAME);
            String username = response.getString(VolleyRequest.USERNAME);
            String profileImage = response.getString(VolleyRequest.PROFILE_IMAGE_URL);
            return new User.UserHolder(id, displayName, username, profileImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
