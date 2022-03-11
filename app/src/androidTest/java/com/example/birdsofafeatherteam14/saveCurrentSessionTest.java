package com.example.birdsofafeatherteam14;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.doesNotHaveFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class saveCurrentSessionTest {

    @Test
    public void mainActivitySavesSessionTest() {
        int sesh = MainActivity.CURRENT_USER_SESSION_ID;
        if(sesh == -1) {
            onView(withId(R.id.sessionNameText)).check((ViewAssertion) doesNotExist());
        }
        else {
            onView(withId(R.id.sessionNameText)).check((ViewAssertion) isDisplayed());
        }
    }

}
