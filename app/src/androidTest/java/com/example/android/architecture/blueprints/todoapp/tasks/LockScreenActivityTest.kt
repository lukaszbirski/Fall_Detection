package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.presentation.LockScreenActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * End-to-End tests for the LockScreenActivity.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class LockScreenActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(LockScreenActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun launchLockScreenActivity_allElementsAreDisplayed() {
        onView(withId(R.id.fallDetectedTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.countdownTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.progressCountDown)).check(matches(isDisplayed()))
        onView(withId(R.id.counterFragmentButton)).check(matches(isDisplayed()))
    }

    @Test
    fun whenCountdownPressButton_navigateToHomeFragment(): Unit = runBlocking {
        // wait 5 sec
        delay(5000)
        // click OK button
        onView(withId(R.id.counterFragmentButton)).perform(click())
        // check if navigated to home fragment
        onView(withText("TEST")).check(matches(isDisplayed()))
    }

    @Test
    fun whenCountdownDoNotPressButton_displayDialogAndNavigateToHomeFragment(): Unit = runBlocking {
        // wait 65 sec
        delay(65000)
        // checks if dialog is displayed
        onView(ViewMatchers.isRoot()).inRoot(RootMatchers.isDialog())
            .check(matches(isDisplayed()))
        // checks if data are displayed in dialog
        onView(withId(R.id.counterTimeOutTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.counterTimeOutImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.messageSentTextView)).check(matches(isDisplayed()))
        onView(withText(R.string.time_out_dialog_exit_text)).check(matches(isDisplayed()))
        onView(withText(R.string.time_out_dialog_exit_text)).check(matches(isEnabled()))
        // clicks exit button
        onView(withText(R.string.time_out_dialog_exit_text)).perform(click())
        // check if navigated to home fragment
        onView(withText("TEST")).check(matches(isDisplayed()))
    }
}
