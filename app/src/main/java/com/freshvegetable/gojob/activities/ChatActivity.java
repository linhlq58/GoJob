package com.freshvegetable.gojob.activities;

import android.app.Activity;
import android.app.Notification;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.MessageAdapter;
import com.freshvegetable.gojob.fragments.EndingSoonFragment;
import com.freshvegetable.gojob.models.*;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.Utils;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by duyti on 8/29/2016.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EVENT_NEW_MESSAGE = "chatMessage";
    private static final String EVENT_SENT_MESSAGE = "privateMessage";

    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.zzz'Z'", Locale.ENGLISH);

    private RecyclerView rcMessage;
    private EditText etMessage;
    private ImageView ivSend;
    private TextView tvOldMessage;
    private ProgressBar loadOldMessage;
    private String idReceiver = "";
    Date dateToday = new Date(System.currentTimeMillis());

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Url.BASE_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject msg = (JSONObject) args[0];
                    try {
                        String created = msg.getString("created");
                        String timeResult = "";
                        Date mDate = new Date();
                        try {
                            mDate = mDateFormat.parse(created);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        timeResult = mDate.getHours() + ":" + mDate.getMinutes();
                        String mSender = msg.getString("sender");
                        String username = msg.getString("username");
                        String profileImageUrl = msg.getString(VolleyRequest.PROFILE_IMAGE_URL);
                        if (profileImageUrl.substring(0, 1).equals(".")) {
                            profileImageUrl = Url.BASE_URL + "/" + profileImageUrl.substring(2);
                        } else {
                            profileImageUrl = Url.BASE_URL + "/" + profileImageUrl;
                        }
                        String data = msg.getString("text");
                        if (mSender.equals(idReceiver)) {
                            addMessage(username, data, Message.TYPE_MESSAGE_OTHER, timeResult, profileImageUrl);
                        } else {
                            addMessage(username, data, Message.TYPE_MESSAGE, timeResult, profileImageUrl);
                        }
                        messageAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    private MessageAdapter messageAdapter;
    private List<Message> listMessage = new ArrayList<>();

    private ImageLoader mImageLoader;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idReceiver = getIntent().getStringExtra(EndingSoonFragment.KEY_USER_ID);
        initSocket();
        setContentView(R.layout.activity_chat);
        setToolBar();
        setUpNetworkConnection();
        initView();
    }

    private void initSocket() {
        mSocket.on(EVENT_NEW_MESSAGE, onNewMessage);
        mSocket.connect();
    }

    private void initView() {
        tvOldMessage = (TextView) findViewById(R.id.tv_old_message);
        loadOldMessage = (ProgressBar) findViewById(R.id.load_old_msg);
        tvOldMessage.setOnClickListener(this);
        rcMessage = (RecyclerView) findViewById(R.id.messages);
        etMessage = (EditText) findViewById(R.id.message_input);
        ivSend = (ImageView) findViewById(R.id.send_button);
        //init adapter
        messageAdapter = new MessageAdapter(listMessage, mImageLoader);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcMessage.setLayoutManager(mLayoutManager);
        rcMessage.setAdapter(messageAdapter);
        ivSend.setOnClickListener(this);
        messageAdapter.notifyDataSetChanged();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().show();
    }

    private void setUpNetworkConnection() {
        if (Utils.isNetworkConnected(this)) {
            mQueue = Volley.newRequestQueue(this);
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
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                String data = etMessage.getText().toString();
                String receiver = idReceiver;
                JSONObject msg = new JSONObject();
                try {
                    msg.put("text", data);
                    msg.put("receiver", receiver);
                    mSocket.emit(EVENT_SENT_MESSAGE, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_old_message:
                getOldMessage();
                break;
            default:
                break;
        }
    }

    private void scrollToBottom() {
        rcMessage.scrollToPosition(messageAdapter.getItemCount() - 1);
    }

    private void addMessage(String user, String msg, int type, String time, String avatarURL) {
        if (TextUtils.isEmpty(msg)) {
            etMessage.requestFocus();
            return;
        }
        Message message = new Message();
        message.setmUsername(user);
        message.setmMessage(msg);
        message.setmTime(time);
        message.setmType(type);
        message.setmAvatarURL(avatarURL);
        listMessage.add(message);

        etMessage.setText("");
        messageAdapter.notifyItemInserted(listMessage.size() - 1);
        scrollToBottom();
    }

    private void getOldMessage() {
        tvOldMessage.setVisibility(View.GONE);
        loadOldMessage.setVisibility(View.VISIBLE);

        listMessage.clear();
        final String url = Url.BASE_URL + Url.MASSAGE_HISTORY_URL + idReceiver;
        JsonObjectRequest contactRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject infoReceiver = response.getJSONObject("receiver");
                            //get info
                            String username = infoReceiver.getString(VolleyRequest.USERNAME);
                            Log.i("User", username);
                            String profileImgUrl = infoReceiver.getString(VolleyRequest.PROFILE_IMAGE_URL);
                            if (profileImgUrl.substring(0, 1).equals(".")) {
                                profileImgUrl = Url.BASE_URL + "/" + profileImgUrl.substring(2);
                            } else {
                                profileImgUrl = Url.BASE_URL + "/" + profileImgUrl;
                            }
                            //get list old message
                            JSONArray listOldMsg = response.getJSONArray("messages");
                            for (int i = listOldMsg.length() - 1; i >= 0; i--) {
                                JSONObject mJSONObject = listOldMsg.getJSONObject(i);
                                try {
                                    String receiver = mJSONObject.getString("receiveId");
                                    String mess = mJSONObject.getString("content");
                                    String created = mJSONObject.getString(VolleyRequest.CREATED);
                                    SimpleDateFormat format = new SimpleDateFormat(
                                            "yyyy-MM-dd");
                                    String today = format.format(dateToday);
                                    if (today.equals(created.substring(0,created.indexOf("T")))) {
                                        created = created.substring(created.indexOf("T")+1,created.indexOf("."));
                                    } else {
                                        created = created.substring(0,created.indexOf("T"));
                                    }
                                    if (receiver.equals(idReceiver)) {
                                        SharedPreferences share = getSharedPreferences("MyAccount", MODE_PRIVATE);
                                        //Lấy chuỗi String trong file SharedPreferences thông qua tên URName và URPass
                                        String name = share.getString("URName", "");
                                        String profileImgURL = share.getString("URProfileImageURL", "");
                                        if (profileImgURL.substring(0, 1).equals(".")) {
                                            profileImgURL = Url.BASE_URL + "/" + profileImgURL.substring(2);
                                        } else {
                                            profileImgURL = Url.BASE_URL + "/" + profileImgURL;
                                        }
                                        addMessage(name, mess, Message.TYPE_MESSAGE, created, profileImgURL);
                                    } else {
                                        addMessage(username, mess, Message.TYPE_MESSAGE_OTHER, created, profileImgUrl);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loadOldMessage.setVisibility(View.GONE);
                        messageAdapter.notifyDataSetChanged();
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
}
