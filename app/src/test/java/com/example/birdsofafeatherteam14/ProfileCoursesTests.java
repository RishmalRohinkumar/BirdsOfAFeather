package com.example.birdsofafeatherteam14;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static org.junit.Assert.*;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.model.IStudent;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProfileCoursesTests {
    @Rule
    public ActivityScenarioRule<ProfileCoursesActivity> scenarioRule = new ActivityScenarioRule<ProfileCoursesActivity>(ProfileCoursesActivity.class);

    @Test
    public void testInitialDisplay() {
        ActivityScenario<ProfileCoursesActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText subject_text = activity.findViewById(R.id.enter_subject);
            EditText course_number_text = activity.findViewById(R.id.enter_course_number);
            EditText quarter_text = activity.findViewById(R.id.enter_quarter);
            EditText year_text = activity.findViewById(R.id.enter_year);

            assertEquals("", subject_text.getText().toString());
            assertEquals("", course_number_text.getText().toString());
            assertEquals("", quarter_text.getText().toString());
            assertEquals("", year_text.getText().toString());
        });
    }

    @Test
    public void testUpdateDisplay() {
        ActivityScenario<ProfileCoursesActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView error = activity.findViewById(R.id.error_textview);
            EditText subject_text = activity.findViewById(R.id.enter_subject);
            EditText course_number_text = activity.findViewById(R.id.enter_course_number);
            EditText quarter_text = activity.findViewById(R.id.enter_quarter);
            EditText year_text = activity.findViewById(R.id.enter_year);
            Button enterButton = activity.findViewById(R.id.enter_button);

            subject_text.setText("CSE");
            course_number_text.setText("11");
            quarter_text.setText("Summer");
            year_text.setText("2022");
            enterButton.performClick();

            assertEquals("That is an invalid Quarter.", error.getText());
        });
    }
}
