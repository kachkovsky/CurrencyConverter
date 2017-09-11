package ru.kachkovsky.curcon;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.kachkovsky.curcon.activity.CurrencyActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MinimumToCheckCalculationsOnButtonCLickInstrumentedTest {
    @Rule
    public ActivityTestRule<CurrencyActivity> mActivityRule = new ActivityTestRule<>(
            CurrencyActivity.class);

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextAmountOfCurrencyFrom))
                .perform(typeText("4646,784"), closeSoftKeyboard());
        onView(withId(R.id.buttonApply)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.editTextAmountOfCurrencyTo))
                .check(matches(withText("4646,78")));
    }
}
