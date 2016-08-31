package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nam on 8/4/2016.
 *
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
    @BindView(R.id.container)
    RelativeLayout container;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(SignUpActivity.this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @OnClick({R.id.btnSignUp, R.id.tvSignIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                // Checking network connection
                if (!Utils.isNetworkConnected(SignUpActivity.this)) {
                    Snackbar.make(container,
                            "No network connection", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                // Checking other information
                String firstName = etSignUpFirstName.getText().toString();
                String lastName = etSignUpLastName.getText().toString();
                String email = etSignUpEmail.getText().toString();
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();

                // Checking if firstName is empty
                if (firstName.equals("")) {
                    Snackbar.make(container,
                            "First Name not be empty", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                // Checking if lastName is empty
                if (lastName.equals("")) {
                    Snackbar.make(container,
                            "Last Name can not be empty", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                // Checking if email is empty
                if (email.equals("")) {
                    Snackbar.make(container,
                            "Must have a email", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                // Checking if userName is empty
                if (username.equals("")) {
                    Snackbar.make(container,
                            "Username can not be empty", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                // Checking password requirement
                // Password must have 10-16 characters
                // and contain at least on of those type:
                // uppercase, lowercase, digit number and special symbol (!,@,#...)
                int lowerCaseCount = 0;
                int upperCaseCount = 0;
                int digitCount = 0;
                int specialSymbolCount = 0;
                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    if (Character.isUpperCase(c)) {
                        upperCaseCount++;
                    }
                    if (Character.isLowerCase(c)) {
                        lowerCaseCount++;
                    }
                    if (Character.isDigit(c)) {
                        digitCount++;
                    }
                    if (c >= 33 && c <= 46 || c == 64) {
                        specialSymbolCount++;
                    }
                }

                if (password.length() < 10 || lowerCaseCount == 0 || upperCaseCount == 0
                        || digitCount == 0 || specialSymbolCount == 0) {
                    Snackbar.make(container,
                            "Password must have at least 10 character, and contains upper, lower case, digit and special Symbol", Snackbar.LENGTH_SHORT).show();
                    break;
                }


                HashMap<String, String> param = new HashMap<>();
                param.put(VolleyRequest.FIRST_NAME, etSignUpFirstName.getText().toString());
                param.put(VolleyRequest.LAST_NAME, etSignUpLastName.getText().toString());
                param.put(VolleyRequest.EMAIL, etSignUpEmail.getText().toString());
                param.put(VolleyRequest.USERNAME, etSignUpUsername.getText().toString());
                param.put(VolleyRequest.PASSWORD, etSignUpPassword.getText().toString());

                JSONObject signUpParam = new JSONObject(param);

                String url = Url.BASE_URL + Url.SIGN_UP_API_URL;
                JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, url,
                        signUpParam,
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
