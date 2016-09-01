package com.freshvegetable.gojob.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.activities.ChatActivity;
import com.freshvegetable.gojob.adapters.ContactAdapter;
import com.freshvegetable.gojob.models.Contact;
import com.freshvegetable.gojob.models.Post;
import com.freshvegetable.gojob.models.User;
import com.freshvegetable.gojob.utils.CustomRequest;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.Utils;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by Nam on 8/15/2016.
 */
public class EndingSoonFragment extends Fragment implements ContactAdapter.OnClickItemView, View.OnClickListener {

    public static final String KEY_USER_ID = "KEY_USER_ID";
    private RecyclerView lvContact;
    private SwipeRefreshLayout contactListRefresher;
    private List<Contact> listContact = new ArrayList<>();

    private RequestQueue mQueue;
    private ContactAdapter mAdapterContact;
    private ImageLoader mImageLoader;
    private EditText etSearch;
    private ImageView ivSearch;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.zzz'Z'", Locale.ENGLISH);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(rootView);
        setUpNetworkConnection();
        mAdapterContact = new ContactAdapter(listContact, mImageLoader);
        mAdapterContact.setOnClickItemView(this);
        setupView();
        mAdapterContact.notifyDataSetChanged();
        contactListRefresher.setColorSchemeResources(R.color.colorPrimary);
        contactListRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getContactFromServer();
                mAdapterContact.notifyDataSetChanged();
                contactListRefresher.setRefreshing(false);
            }
        });

        return rootView;
    }

    private void initView(View rootView) {
        contactListRefresher = (SwipeRefreshLayout) rootView.findViewById(R.id.contactListRefresher);
        lvContact = (RecyclerView) rootView.findViewById(R.id.contactList);
        etSearch = (EditText) rootView.findViewById(R.id.et_search);
        ivSearch = (ImageView) rootView.findViewById(R.id.search_button);
        ivSearch.setOnClickListener(this);
    }

    private void setupView() {
        lvContact.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        lvContact.setLayoutManager(mLayoutManager);
        getContactFromServer();
        lvContact.setItemAnimator(new DefaultItemAnimator());
        lvContact.setAdapter(mAdapterContact);
    }

    private void addContact(String id, String username, String avatarURL, String displayName, String created) {
        listContact.add(new Contact(id, username, displayName, avatarURL, created));
    }


    private void getContactFromServer() {
        listContact.clear();
        final String url = Url.BASE_URL + Url.GET_CHAT_LIST_HISTORY_URL;
        JsonArrayRequest contactRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("SIZE", response.length() + "");
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject mJSONObject = response.getJSONObject(i);
                                Log.i("OBJ", mJSONObject.toString());
                                String id = mJSONObject.getString(VolleyRequest.ID);
                                String username = mJSONObject.getString(VolleyRequest.USERNAME);
                                String displayName = mJSONObject.getString(VolleyRequest.DISPLAY_NAME);
                                String profileImgUrl = mJSONObject.getString(VolleyRequest.PROFILE_IMAGE_URL);
                                if(profileImgUrl.substring(0,1).equals(".")){
                                    profileImgUrl = Url.BASE_URL+"/"+profileImgUrl.substring(2);
                                }else{
                                    profileImgUrl = Url.BASE_URL+"/"+profileImgUrl;
                                }
                                String created = mJSONObject.getString(VolleyRequest.CREATED);
                                Log.i("DATE", created);

                                Date dateToday = new Date(System.currentTimeMillis());
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String today = format.format(dateToday);
                                if (today.equals(created.substring(0,created.indexOf("T")))) {
                                    created = created.substring(created.indexOf("T")+1,created.indexOf("."));
                                } else {
                                    created = created.substring(0,created.indexOf("T"));
                                }
                                addContact(id, username, profileImgUrl, displayName, created);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapterContact.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
        mQueue.add(contactRequest);
    }

    private void setUpNetworkConnection() {
        if (Utils.isNetworkConnected(this.getContext())) {
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
        } else
            Toast.makeText(this.getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(), ChatActivity.class);
        intent.putExtra(KEY_USER_ID, listContact.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_button:
                listContact.clear();
                final String url = Url.BASE_URL + Url.FIND_USER_URL;
                JSONObject request = new JSONObject();
                try {
                    request.put(VolleyRequest.TEXT_SEARCH, etSearch.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomRequest searchRequest = new CustomRequest(Request.Method.POST, url ,
                        request ,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.i("SIZE", response.length() + "");
                                for (int i = 0; i < response.length(); i++) {

                                    try {
                                        JSONObject mJSONObject = response.getJSONObject(i);
                                        Log.i("SEARCH", mJSONObject.toString());
                                        String id = mJSONObject.getString(VolleyRequest.ID);
                                        String username = mJSONObject.getString(VolleyRequest.USERNAME);
                                        String displayName = mJSONObject.getString(VolleyRequest.DISPLAY_NAME);
                                        String profileImgUrl = mJSONObject.getString(VolleyRequest.PROFILE_IMAGE_URL);
                                        if(profileImgUrl.substring(0,1).equals(".")){
                                            profileImgUrl = Url.BASE_URL+"/"+profileImgUrl.substring(2);
                                        }else{
                                            profileImgUrl = Url.BASE_URL+"/"+profileImgUrl;
                                        }
                                        String created = mJSONObject.getString(VolleyRequest.CREATED);
                                        Log.i("DATE", created);

                                        Date dateToday = new Date(System.currentTimeMillis());
                                        SimpleDateFormat format = new SimpleDateFormat(
                                                "yyyy-MM-dd");
                                        String today = format.format(dateToday);
                                        if (today.equals(created.substring(0,created.indexOf("T")))) {
                                            created = created.substring(created.indexOf("T")+1,created.indexOf("."));
                                        } else {
                                            created = created.substring(0,created.indexOf("T"));
                                        }
                                        addContact(id, username, profileImgUrl, displayName, created);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                mAdapterContact.notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.toString());
                            }
                        });
                mQueue.add(searchRequest);
                break;
            default:
                break;
        }
    }
}
