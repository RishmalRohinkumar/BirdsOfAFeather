package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfilePictureActivity extends AppCompatActivity {

    public static final int PICTURE_ACTIVITY_CODE = 0;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        //Default Picture
        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPGyOeoFf4IcQNhHFWSkaYWgwDUSdpMCw-3A&usqp=CAU";
        if(URLUtil.isValidUrl(url)){
            ImageView pic = (ImageView) findViewById(R.id.student_pic);
            Picasso.get().load(url).resize(175,175).into(pic);
        }

    }

    public void onSaveClicked(View view) {

        String url = findViewById(R.id.student_pic_url).toString();
        if(URLUtil.isValidUrl(url)) {
            ImageView pic = (ImageView) findViewById(R.id.student_pic);
            Picasso.get().load(url).resize(175, 175).into(pic);
        }

    }


    public void onGoBackClicked(View view){

        Intent returnIntent = new Intent();
        returnIntent.putExtra("url",url);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}