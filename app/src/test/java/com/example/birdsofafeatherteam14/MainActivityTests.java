package com.example.birdsofafeatherteam14;


import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTests{
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    // Test to make sure the app starts out by not searching
    @Test
    public void testStartNotSearching() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button searchButton = activity.findViewById(R.id.searchButton);

            assertEquals("Start", searchButton.getText().toString());
        });
    }
}
