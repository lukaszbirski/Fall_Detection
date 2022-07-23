package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
        onView(ViewMatchers.withText("TEST")).check(matches(isDisplayed()))
    }

//    delay(120000)       // 120 sec
}
