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

public class ProfilePictureActivity extends AppCompatActivity {

    public static final int PICTURE_ACTIVITY_CODE = 0;
    String current_url = "";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        Intent intent = getIntent();
        this.name = intent.getStringExtra("student_name");

        //Default Picture
        String default_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPGyOeoFf4IcQNhHFWSkaYWgwDUSdpMCw-3A&usqp=CAU";
        setImage(default_url);
    }
    // Helper Method to encapsulate setting the ImageView to the URL specified in the parameter
    private void setImage(String url) {
        if(checkURLValidity(url)){
            ImageView pic = (ImageView) findViewById(R.id.student_pic);
            Picasso.get().load(url).resize(175,175).into(pic);
            current_url = url;
        } else {
            showAlert( this,"Invalid URL!");
        }
    }

    // Helper method to encapsulate checking if a URL is valid
    // Unsure about this v
    // https://stackoverflow.com/questions/47587755/how-to-check-if-the-url-is-valid-in-android/47588559
    private boolean checkURLValidity(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public void onSaveClicked(View view) {
        EditText urlInput = (EditText) findViewById(R.id.student_pic_url);
        String url = urlInput.getText().toString();
        setImage(url);
    }


    public void onContinueClicked(View view){
        // Continue onto the course selection activity passing along the name and url to it
        Intent intent = new Intent(this, ProfileCoursesActivity.class);
        intent.putExtra("student_name", this.name);
        intent.putExtra("student_url", this.current_url);
        startActivity(intent);
    }
}