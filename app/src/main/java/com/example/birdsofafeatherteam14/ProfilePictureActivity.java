package com.example.birdsofafeatherteam14;

import static com.example.birdsofafeatherteam14.Utilities.showAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProfilePictureActivity extends AppCompatActivity implements ProfilePictureActivityPresenter.View {

    private ProfilePictureActivityPresenter presenter;
    public static final int PICTURE_ACTIVITY_CODE = 0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        Intent intent = getIntent();
        this.name = intent.getStringExtra("student_name");

        // Create default picture
        IPicture defaultPicture = new URLPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPGyOeoFf4IcQNhHFWSkaYWgwDUSdpMCw-3A&usqp=CAU");

        this.presenter = new ProfilePictureActivityPresenter(defaultPicture, this);
    }

    // Helper Method to encapsulate setting the ImageView to the URL specified in the parameter
    @Override
    public void setPicture(IPicture picture) {
        if(picture.isValid()){
            ImageView pic = (ImageView) findViewById(R.id.student_pic);
            Picasso.get().load(picture.getString()).resize(175,175).into(pic);
        } else {
            showAlert( this,"Invalid URL!");
        }
    }

    // Saves the current picture and updates it
    public void onSaveClicked(View view) {
        EditText urlInput = (EditText) findViewById(R.id.student_pic_url);
        String url = urlInput.getText().toString();
        presenter.updatePicture(new URLPicture(url));
    }

    // Leave this activity (view) and send relevant information
    public void onGoBackClicked(View view){
        // Continue onto the course selection activity passing along the name and url to it
        Intent intent = new Intent(this, ProfileCoursesActivity.class);
        intent.putExtra("student_name", this.name);
        intent.putExtra("student_url", this.presenter.getPicture().getString());
        startActivity(intent);
    }
}