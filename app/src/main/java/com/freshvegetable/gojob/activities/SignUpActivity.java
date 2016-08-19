package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.freshvegetable.gojob.R;
//import com.freshvegetable.gojob.utils.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

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

    private AsyncHttpClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();
    }

    @OnClick({R.id.btnSignUp, R.id.tvSignIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
//                String url = Url.BASE_URL + Url.SIGN_UP_API_URL;
                RequestParams params = new RequestParams();
                params.put("firstName", etSignUpFirstName.getText().toString());
                params.put("lastName", etSignUpLastName.getText().toString());
                params.put("email", etSignUpEmail.getText().toString());
                params.put("username", etSignUpUsername.getText().toString());
                params.put("password", etSignUpPassword.getText().toString());
//                client.post(SignUpActivity.this, url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        String response = new  String(responseBody, StandardCharsets.UTF_8);
//                        try {
//                            JSONObject jObject = new JSONObject(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                    }
//                });

                break;
            case R.id.tvSignIn:
                this.finish();
                break;
        }
    }
}
