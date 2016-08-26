package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.freshvegetable.gojob.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import android.support.v7.app.ActionBarActivity;

/**
 * Created by NamVp on 18/08/2016.
 */
public class NewPostActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnGetPhoto)
    Button btnGetPhoto;
    @BindView(R.id.btnGetCamera)
    Button btnGetCamera;
    @BindView(R.id.btnPost)
    Button btnPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnGetPhoto, R.id.btnGetCamera, R.id.btnPost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetPhoto:
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
                break;
            case R.id.btnGetCamera:
                intent = new Intent(NewPostActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.btnPost:
                break;
        }
    }
}
