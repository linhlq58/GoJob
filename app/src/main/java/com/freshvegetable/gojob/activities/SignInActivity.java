package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.VolleyRequest;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NamVp on 02/08/2016.
 */
public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.etSignInUsername)
    EditText etSignInUsername;
    @BindView(R.id.etSignInPassword)
    EditText etSignInPassword;
    @BindView(R.id.container)
    RelativeLayout container;
    private AsyncHttpClient client = null;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
    }

    @OnClick({R.id.btnSignIn, R.id.btnSignUp, R.id.tvForgetPassword, R.id.btnFacebook, R.id.btnGooglePlus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                if (etSignInUsername.getText().toString().equals("")) {
                    Snackbar.make(container, "Please enter your Username", Snackbar.LENGTH_SHORT).
                            setAction("OKAY", null).show();
                } else if (etSignInPassword.getText().toString().equals("")) {
                    Snackbar.make(container, "Enter your password", Snackbar.LENGTH_SHORT).
                            setAction("OKAY", null).show();
                } else {

                    String url = Url.BASE_URL + Url.SIGN_IN_API_URL;
                    Map<String, String> mParam = new HashMap<>();
//                    mParam.put(VolleyRequest.USERNAME, etSignInUsername.getText().toString());
//                    mParam.put(VolleyRequest.PASSWORD, etSignInPassword.getText().toString());
                    mParam.put(VolleyRequest.USERNAME, "blahblahblah");
                    mParam.put(VolleyRequest.PASSWORD, "Blah_123blah");
                    Log.d("username", etSignInUsername.getText().toString());
                    Log.d("password", etSignInPassword.getText().toString());

                    mQueue = Volley.newRequestQueue(SignInActivity.this);
                    JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url,
                            new JSONObject(mParam),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Log.d("result", response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Snackbar.make(container, "Username of password is incorrect", Snackbar.LENGTH_SHORT).setAction("RETRY", null).show();
                                    Log.e("Error:", error.toString());
                                }
                            }
                    );
                    mQueue.add(loginRequest);
                }

                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
        }

    }

}
