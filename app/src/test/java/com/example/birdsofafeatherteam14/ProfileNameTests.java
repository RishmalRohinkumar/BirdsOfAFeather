package com.example.birdsofafeatherteam14;

import android.widget.Button;
import android.widget.EditText;
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
public class ProfileNameTests {
    @Rule
    public ActivityScenarioRule<ProfileNameActivity> scenarioRule =
            new ActivityScenarioRule<ProfileNameActivity>(ProfileNameActivity.class);

    // Test to make sure that the display is initialized correctly
    @Test
    public void testInitialDisplay() {
        ActivityScenario<ProfileNameActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText nameText = (EditText) activity.findViewById(R.id.editTextTextPersonName);
            String name = nameText.getText().toString();

            assertEquals("", name);
        });
    }

    // Test to make sure empty name input fails
    @Test
    public void testEmptyNameInput() {
        ActivityScenario<ProfileNameActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView error = activity.findViewById(R.id.invalid_name_textview);
            EditText name_text = activity.findViewById(R.id.editTextTextPersonName);
            Button submit_button = activity.findViewById(R.id.enter_name_button);

            name_text.setText("");
            submit_button.performClick();

            String error_text = error.getText().toString();
            assertEquals("You must enter a name", error_text);
        });
    }

    // Test to make sure valid name input is successfull
    @Test
    public void testValidNameInput() {
        ActivityScenario<ProfileNameActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView error = activity.findViewById(R.id.invalid_name_textview);
            EditText name_text = activity.findViewById(R.id.editTextTextPersonName);
            Button submit_button = activity.findViewById(R.id.enter_name_button);

            name_text.setText("Tyler");
            submit_button.performClick();

            String error_text = error.getText().toString();
            assertEquals("", error_text);
        });
    }
}
