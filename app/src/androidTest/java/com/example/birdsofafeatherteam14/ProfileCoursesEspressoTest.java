package com.example.birdsofafeatherteam14;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.birdsofafeatherteam14.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileCoursesEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void profileCoursesEspressoTest() {
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.editTextTextPersonName),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatEditText.perform(replaceText("Rishmal"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.enter_name_button), withText("Enter"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        ViewInteraction materialButton2 = onView(
//                allOf(withId(R.id.backButton), withText("Continue"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        materialButton2.perform(click());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.enter_subject),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                8),
//                        isDisplayed()));
//        appCompatEditText2.perform(replaceText("CSE"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.enter_course_number),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                9),
//                        isDisplayed()));
//        appCompatEditText3.perform(replaceText("110"), closeSoftKeyboard());
//
//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.enter_quarter),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                10),
//                        isDisplayed()));
//        appCompatSpinner.perform(click());
//
//        DataInteraction appCompatCheckedTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(1);
//        appCompatCheckedTextView.perform(click());
//
//        ViewInteraction appCompatSpinner2 = onView(
//                allOf(withId(R.id.enter_size),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                12),
//                        isDisplayed()));
//        appCompatSpinner2.perform(click());
//
//        DataInteraction appCompatCheckedTextView2 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(4);
//        appCompatCheckedTextView2.perform(click());
//
//        ViewInteraction appCompatSpinner3 = onView(
//                allOf(withId(R.id.enter_year),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                11),
//                        isDisplayed()));
//        appCompatSpinner3.perform(click());
//
//        DataInteraction appCompatCheckedTextView3 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(0);
//        appCompatCheckedTextView3.perform(click());
//
//        ViewInteraction materialButton3 = onView(
//                allOf(withId(R.id.enter_button), withText("Enter"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                14),
//                        isDisplayed()));
//        materialButton3.perform(click());
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.stored_courses_display), withText("CSE 110 Winter 2022, Huge\n"),
//                        withParent(withParent(withId(android.R.id.content))),
//                        isDisplayed()));
//        textView.check(matches(withText("CSE 110 Winter 2022, Huge ")));
//
//        ViewInteraction editText = onView(
//                allOf(withId(R.id.enter_subject), withText("CSE"),
//                        withParent(withParent(withId(android.R.id.content))),
//                        isDisplayed()));
//        editText.check(matches(withText("CSE")));
//
//        ViewInteraction editText2 = onView(
//                allOf(withId(R.id.enter_course_number), withText("110"),
//                        withParent(withParent(withId(android.R.id.content))),
//                        isDisplayed()));
//        editText2.check(matches(withText("110")));
//
//        ViewInteraction textView2 = onView(
//                allOf(withId(android.R.id.text1), withText("Winter"),
//                        withParent(allOf(withId(R.id.enter_quarter),
//                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
//                        isDisplayed()));
//        textView2.check(matches(withText("Winter")));
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(android.R.id.text1), withText("2022"),
//                        withParent(allOf(withId(R.id.enter_year),
//                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
//                        isDisplayed()));
//        textView3.check(matches(withText("2022")));
//
//        ViewInteraction textView4 = onView(
//                allOf(withId(android.R.id.text1), withText("Huge (250-400)"),
//                        withParent(allOf(withId(R.id.enter_size),
//                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
//                        isDisplayed()));
//        textView4.check(matches(withText("Huge (250-400)")));
//
//        ViewInteraction textView5 = onView(
//                allOf(withId(R.id.error_textview), withText("Added quarter"),
//                        withParent(withParent(withId(android.R.id.content))),
//                        isDisplayed()));
//        textView5.check(matches(withText("Added quarter")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
