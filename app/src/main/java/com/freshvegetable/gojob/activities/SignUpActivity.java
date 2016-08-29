package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.freshvegetable.gojob.utils.Url;

/**
 * Created by Nam on 8/4/2016.
 */
public class SignUpActivity extends AppCompatActivity {


    @BindView(R.id.etSignUpFirstName)
    EditText etSignUpFirstName;
    @BindView(R.id.etSignUpEmail)
    EditText etSignUpEmail;
    @BindView(R.id.etSignUpUsername)
    EditText etSignUpUsername;
    @BindView(R.id.etSignUpPassword)
    EditText etSignUpPassword;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.tvSignIn)
    TextView tvSignIn;
    @BindView(R.id.etSignUpLastName)
    EditText etSignUpLastName;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(SignUpActivity.this);
    }

    @OnClick({R.id.btnSignUp, R.id.tvSignIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                String url = Url.BASE_URL + Url.SIGN_UP_API_URL;
                HashMap<String, String> param = new HashMap<>();
                param.put(VolleyRequest.FIRST_NAME, etSignUpFirstName.getText().toString());
                param.put(VolleyRequest.LAST_NAME, etSignUpLastName.getText().toString());
                param.put(VolleyRequest.EMAIL, etSignUpEmail.getText().toString());
                param.put(VolleyRequest.USERNAME, etSignUpUsername.getText().toString());
                param.put(VolleyRequest.PASSWORD, etSignUpPassword.getText().toString());

                JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(param),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("Error", volleyError.toString());
                            }
                        }
                );
                mQueue.add(signUpRequest);

                break;
            case R.id.tvSignIn:
                this.finish();
                break;
        }
    }
}
