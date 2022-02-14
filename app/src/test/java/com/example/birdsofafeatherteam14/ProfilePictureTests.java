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

@RunWith(AndroidJUnit4.class)
public class ProfilePictureTests {

    @Rule
    public ActivityScenarioRule<ProfilePictureActivity> scenarioRule = new ActivityScenarioRule<ProfilePictureActivity>(ProfilePictureActivity.class);

    @Test
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



}
