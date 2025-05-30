package com.example.codingassessment

import android.content.pm.ActivityInfo
import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UiEspressoTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editTextHintIsCorrectTest() {
        onView(withId(R.id.editText))
            .check(matches(withHint("Enter text here")))
    }

    @Test
    fun okButtonIsDisplayedTest() {
        onView(withId(R.id.okButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewVisibleTest() {
        onView(withId(R.id.textEntriesListView))
            .check(matches(isDisplayed()))

    }

    /* Three rudimentary tests that I built to learn the basics of Espresso and to see if Espresso
       can see the the edit text box, the button and the List of text entries.
     */

    @Test
    fun addTextEntryTest() {
        onView(withId(R.id.editText)).perform(typeText("Hello World"))
        onView(withId(R.id.okButton)).perform(click())
        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Hello World"))))

    }
    /* Test to see that a message that the user has entered and pressed Ok is appearing in the text
       entries list
     */

    @Test
    fun displaySuccessToastForSuccessfulTextEntryTest() {
        var decorView: View? = null

        activityRule.scenario.onActivity {
            decorView = it.window.decorView
        }

        onView(withId(R.id.editText)).perform(typeText("Hello World"))
        onView(withId(R.id.okButton)).perform(click())

        onView(withText("Text Entry Successfully Added"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayErrorMessageToastForEmptyTextEntryTest() {
        var decorView: View? = null

        activityRule.scenario.onActivity {
            decorView = it.window.decorView
        }

        onView(withId(R.id.okButton)).perform(click())

        onView(withText("Text Field cannot be empty. Please enter a message"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))

    }
    /* Test that I made to check if the UI can see the Toast message that I have implemented when
       the user enters an empty text entry.
    */

    @Test
    fun whitespaceOnlyInputShowsToastTest() {
        var decorView: View? = null
        activityRule.scenario.onActivity {
            decorView = it.window.decorView
        }

        onView(withId(R.id.editText)).perform(typeText("   "))
        onView(withId(R.id.okButton)).perform(click())

        onView(withText("Text Field cannot be empty. Please enter a message"))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))
    }
    /* Testing that the rejected Toast message appears when a user tries to input whitespace instead
       of a blank message.
    */

    @Test
    fun addMultipleTextEntriesTest() {
        onView(withId(R.id.editText)).perform(typeText("First Message"))
        onView(withId(R.id.okButton)).perform(click())

        onView(withId(R.id.editText)).perform(typeText("Second Message"))
        onView(withId(R.id.okButton)).perform(click())

        onView(withId(R.id.editText)).perform(typeText("Third Message"))
        onView(withId(R.id.okButton)).perform(click())

        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("First Message"))))
            .check(matches(hasDescendant(withText("Second Message"))))
            .check(matches(hasDescendant(withText("Third Message"))))

    }
    // Self explanatory testing multiple text entries

    @Test
    fun trimmingTextWithSpacesTest() {
        onView(withId(R.id.editText)).perform(typeText("  Hello World  "))
        onView(withId(R.id.okButton)).perform(click())


        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Hello World"))))
    }
    // Testing that whitespace is trimmed when a user adds a text entry with whitespace.

    @Test
    fun addTextWithSpecialCharactersTest() {
        onView(withId(R.id.editText)).perform(typeText("!@#$%^&*()_+`~[]{};':\",./<>?"))
        onView(withId(R.id.okButton)).perform(click())

        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("!@#$%^&*()_+`~[]{};':\",./<>?"))))
    }
    // Testing that special characters are accepted when they are within a message of a text entry.


    @Test
    fun addTextEntryPersistsOnScreenRotationTest() {
        onView(withId(R.id.editText)).perform(typeText("Rotation Test"))
        onView(withId(R.id.okButton)).perform(click())


        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Rotation Test"))))
        // Initial check to see that the message has been added to the textEntries List

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        // Rotating the screen to landscape

        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Rotation Test"))))
        /* Checking that the lists still persists and hasn't removed the text entry that has been
            added within the list after screen rotation.
         */

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        // Rotating the screen back to portrait

        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Rotation Test"))))
        // Checking after rotation back to portrait that the text entries st
    }

    @Test
    fun stressTestRapidClicking() {
        onView(withId(R.id.editText)).perform(typeText("Stress Test"))

        repeat(10) {
            onView(withId(R.id.okButton)).perform(click())
        }

        onView(withId(R.id.textEntriesListView))
            .check(matches(hasDescendant(withText("Stress Test"))))

        /* Test that I created to handle stress testing by randomly pressing the ok button after a
           text entry has been added.
        */
    }

}