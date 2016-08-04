package com.freshvegetable.gojob.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.freshvegetable.gojob.R;

import butterknife.OnClick;

/**
 * Created by Nam on 8/4/2016.
 */
public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @OnClick({R.id.btnSignUp, R.id.tvSignIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                break;
            case R.id.tvSignIn:
                this.finish();
                break;
        }
    }
}
