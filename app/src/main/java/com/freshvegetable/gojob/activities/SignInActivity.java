package com.freshvegetable.gojob.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.Utils;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
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

    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieHandler.setDefault(new CookieManager());
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        mQueue = Volley.newRequestQueue(SignInActivity.this);
    }

    @OnClick({R.id.btnSignIn, R.id.btnSignUp, R.id.tvForgetPassword, R.id.btnFacebook, R.id.btnGooglePlus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                //Checking network connection
                if (!Utils.isNetworkConnected(SignInActivity.this)) {
                    Snackbar.make(container, "No network connection", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                //Checking empty username or password
                String username = etSignInUsername.getText().toString();
                String password = etSignInPassword.getText().toString();
                if (username.equals("")) {
                    Snackbar.make(container, "Please enter your Username", Snackbar.LENGTH_SHORT)
                            .setAction("OKAY", null)
                            .show();
                    break;
                }

                if (password.equals("")) {
                    Snackbar.make(container, "Enter your password", Snackbar.LENGTH_SHORT).
                            setAction("OKAY", null).show();
                    break;
                }

                //If all the checking is pass
                //Send request to server

                //sign in url
                String url = Url.BASE_URL + Url.SIGN_IN_API_URL;

                //create param
                Map<String, String> mParam = new HashMap<>();
                mParam.put(VolleyRequest.USERNAME, etSignInUsername.getText().toString());
                mParam.put(VolleyRequest.PASSWORD, etSignInPassword.getText().toString());
                JSONObject signInParam = new JSONObject(mParam);


                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url,
                        signInParam,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(container, "Username of password is incorrect",
                                        Snackbar.LENGTH_SHORT)
                                        .setAction("RETRY", null)
                                        .show();
                                Log.e("Error:", error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        String creds = String.format("%s:%s", "USERNAME", "PASSWORD ");
                        String auth = "Basic " +
                                Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        headers.put("Authorization", auth);
                        return headers;
                    }
                };
                mQueue.add(loginRequest);

                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
        }

    }


    //Hide virtual keyboard function
    public void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
