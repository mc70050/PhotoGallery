package com.example.michael.photogallery;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.EspressoKey;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MyApplicationEspressoTest {
    @Rule
    public ActivityTestRule<FilterActivity> mActivityRule =
            new ActivityTestRule<>(FilterActivity.class);
    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        /*onView(withId(R.id.))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.button2))
                .perform(click());
        // Check that the text was changed.
        onView(withId(R.id.button3))
                .check(matches(withText("HELLO")));
        //onView(withContentDescription("Navigate up")).perform(click());
        */
    }
}