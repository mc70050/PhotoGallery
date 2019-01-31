package com.example.michael.photogallery;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.EspressoKey;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.AndroidJUnitRunner;

import android.view.KeyEvent;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SearchActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ensureSearchWorks() {

        onView(withId(R.id.go_to_search_button)).perform(click());
        // Type text into time from text box
        onView(withId(R.id.timeframe_from_text)).perform(typeText("01/01/2018"), closeSoftKeyboard());
        // Type text into time until text box
        onView(withId(R.id.timeframe_to_text)).perform(typeText("01/02/2019"), closeSoftKeyboard());

        // Click on Search button to start search
        onView(withId(R.id.search_button)).perform(click());

        for (int i = 0; i <= 5; i++) {
            onView(withId(R.id.go_to_right_button)).perform(click());
        }
    }
}
