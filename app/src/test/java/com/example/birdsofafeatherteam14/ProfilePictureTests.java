package com.example.birdsofafeatherteam14;

import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static org.junit.Assert.*;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(AndroidJUnit4.class)
public class ProfilePictureTests {

    //@Rule
    //public ActivityScenarioRule<ProfilePictureActivity> scenarioRule = new ActivityScenarioRule<ProfilePictureActivity>(ProfilePictureActivity.class);


    //We failed to carry out the test because RoboElectric and Picasso has integration error.
    // We were not able to figure out how to Mock picasso
    //However, if Picasso were able to be mocked, this is what some of the tests would look like
    // We would also include a test to make sure that the ImageView didn't change on an invalidURL
    // that wasn't empty, and that it would change the ImageView if there was a valid image URL

    /*@Test
    public void testInitialDisplay() {
        ActivityScenario<ProfilePictureActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
           EditText urlText  = (EditText) activity.findViewById(R.id.student_pic_url);
           String url = urlText.getText().toString();

           assertEquals("", url);
        });
    }

    @Test
    public void testEmptyURLInput() {

        ActivityScenario<ProfilePictureActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText urlText  = (EditText) activity.findViewById(R.id.student_pic_url);
            Button save_button = activity.findViewById(R.id.continueButton);

            ImageView pic = (ImageView) activity.findViewById(R.id.student_pic);
            Drawable picData = pic.getDrawable();

            urlText.setText("");
            save_button.performClick();

            Drawable afterData = pic.getDrawable();

            assertEquals(true, picData.equals(afterData));


        });

    }

    @Test
    public void testInvalidURLInput() {

        ActivityScenario<ProfilePictureActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText urlText  = (EditText) activity.findViewById(R.id.student_pic_url);
            Button save_button = activity.findViewById(R.id.continueButton);

            ImageView pic = (ImageView) activity.findViewById(R.id.student_pic);
            Drawable picData = pic.getDrawable();

            //Made a type within the link
            //https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.industrialempathy.com%2Fposts%2Fimage-optimizations%2F&psig=AOvVaw0LnVw_fm_PlrAV9FZHYdgD&ust=1644899757417000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCOCejKCv_vUCFQAAAAAdAAAAABAI
            urlText.setText("https://www.google.com/url?sa=i&url=https%3A%www.industrialempathy.com%2Fposts%2Fimage-optimizations%2F&psig=AOvVaw0LnVw_fm_PlrAV9FZHYdgD&ust=1644899757417000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCOCejKCv_vUCFQAAAAAdAAAAABAI");
            save_button.performClick();

            Drawable afterData = pic.getDrawable();

            assertEquals(true, picData.equals(afterData));


        });

    }


    @Test
    public void testValidURLInput() {

        ActivityScenario<ProfilePictureActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText urlText  = (EditText) activity.findViewById(R.id.student_pic_url);
            Button save_button = activity.findViewById(R.id.continueButton);

            ImageView pic = (ImageView) activity.findViewById(R.id.student_pic);
            Drawable picData = pic.getDrawable();

            //Made a type within the link
            //https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.industrialempathy.com%2Fposts%2Fimage-optimizations%2F&psig=AOvVaw0LnVw_fm_PlrAV9FZHYdgD&ust=1644899757417000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCOCejKCv_vUCFQAAAAAdAAAAABAI
            urlText.setText("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.industrialempathy.com%2Fposts%2Fimage-optimizations%2F&psig=AOvVaw0LnVw_fm_PlrAV9FZHYdgD&ust=1644899757417000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCOCejKCv_vUCFQAAAAAdAAAAABAI");
            save_button.performClick();

            Drawable afterData = pic.getDrawable();

            assertEquals(false, picData.equals(afterData));


        });

    }*/



}
