package com.freshvegetable.gojob.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.utils.ConstantData;
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
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.container)
    RelativeLayout container;

    private Context mContext;
    private AsyncHttpClient client = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
    }

    @OnClick(R.id.btnSignIn)
    public void onClick(View view) {
        if (etSignInUsername.getText().toString().equals("")) {
            Snackbar.make(container, "Please enter your Username", Snackbar.LENGTH_SHORT).setAction("OKAY", null).show();
        } else if (etSignInPassword.getText().toString().equals("")) {
            Snackbar.make(container, "Enter your password", Snackbar.LENGTH_SHORT).setAction("OKAY", null).show();
        } else {

            String url = ConstantData.BASE_URL + ConstantData.SIGN_IN_API_URL;
            RequestParams params = new RequestParams();
            params.put("username", etSignInUsername.getText().toString());
            params.put("password", etSignInPassword.getText().toString());
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String responce = new String(responseBody, StandardCharsets.UTF_8);
                    try {
                        JSONObject jObject = new JSONObject(responce);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(container, "Sign in Success", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Snackbar.make(container, "Sign in failed", Snackbar.LENGTH_SHORT).setAction("RETRY", null).show();
                }
            });

        }
    }
}
