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

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.utils.Url;
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
                    Snackbar.make(container, "Please enter your Username", Snackbar.LENGTH_SHORT).setAction("OKAY", null).show();
                } else if (etSignInPassword.getText().toString().equals("")) {
                    Snackbar.make(container, "Enter your password", Snackbar.LENGTH_SHORT).setAction("OKAY", null).show();
                } else {

                    String url = Url.BASE_URL + Url.SIGN_IN_API_URL;
                    RequestParams params = new RequestParams();
                    params.put("username", etSignInUsername.getText().toString());
                    params.put("password", etSignInPassword.getText().toString());
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody, StandardCharsets.UTF_8);
                            try {
                                JSONObject jObject = new JSONObject(response);
                                Log.d("result", jObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Snackbar.make(container, "Sign in Success", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Snackbar.make(container, "Username of password is incorrect", Snackbar.LENGTH_SHORT).setAction("RETRY", null).show();

                        }
                    });

                }

                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
        }

    }

}
