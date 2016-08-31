package com.freshvegetable.gojob.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.models.Category;
import com.freshvegetable.gojob.utils.Url;
import com.freshvegetable.gojob.utils.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import android.support.v7.app.ActionBarActivity;

/**
 * Created by NamVp on 18/08/2016.
 *
 */
public class NewPostActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spnCategory)
    AppCompatSpinner spnCategory;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.postPhoto)
    ImageView postPhoto;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.container)
    CoordinatorLayout container;

    private RequestQueue mQueue;


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

        mQueue = Volley.newRequestQueue(NewPostActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    postPhoto.setImageBitmap(yourSelectedImage);
                    postPhoto.setVisibility(View.VISIBLE);
                }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] extractBytes(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @OnClick({R.id.btnGetPhoto, R.id.btnTakePhoto, R.id.btnPost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetPhoto:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
                break;
            case R.id.btnTakePhoto:
                intent = new Intent(NewPostActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.btnPost:
                String title = etTitle.getText().toString();
                if (title.equals("")) {
                    Snackbar.make(container, "Title must not be empty", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                String content = etContent.getText().toString();
                if (content.equals("")) {
                    Snackbar.make(container, "Content must not be empty", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                JSONObject postJSON = new JSONObject();
                try {
                    postJSON.put(VolleyRequest.TITLE, title);
                    postJSON.put(VolleyRequest.POST_CONTENT, content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                int categoryPosition = spnCategory.getSelectedItemPosition();
                String id = Category.Id[categoryPosition];
                JSONObject categoryJSON = new JSONObject();
                try {
                    categoryJSON.put(VolleyRequest.ID, id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("category", categoryJSON.toString());
                try {
                    postJSON.put(VolleyRequest.CATEGORY, categoryJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (postPhoto.getDrawable() != null) {
                    Bitmap img = ((BitmapDrawable) postPhoto.getDrawable()).getBitmap();
                    try {
                        byte[] imgByteArray = extractBytes(img);
                        String base64Image = Base64.encodeToString(imgByteArray, Base64.NO_WRAP);
                        postJSON.put("newPostPicture", base64Image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("post", postJSON.toString());
                String url = Url.BASE_URL + Url.POST_API_URL;
                JsonObjectRequest newPostRequest = new JsonObjectRequest(
                        Request.Method.POST, url, postJSON,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                mQueue.add(newPostRequest);
        }
    }
}
