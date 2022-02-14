package com.example.birdsofafeatherteam14;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MockBluetoothTests {
    @Rule
    public ActivityScenarioRule<MockBluetoothActivity> scenarioRule = new ActivityScenarioRule<MockBluetoothActivity>(MockBluetoothActivity.class);

    @Test
    public void testEnterButton() {
        ActivityScenario<MockBluetoothActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText message = activity.findViewById(R.id.mock_message);
            message.setText("Bill,,,\\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\\n2021,FA,CSE,210\\n2022,WI,CSE,110\\n2022,SP,CSE,110");
            Button button = activity.findViewById(R.id.bluetooth_enter_button);
            button.performClick();

            assertEquals("", message.getText().toString());
//            assertEquals("Bill,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n2021,FA,CSE,210\n2022,WI,CSE,110\n2022,SP,CSE,110", messages.get(0));
        });

    }
}
