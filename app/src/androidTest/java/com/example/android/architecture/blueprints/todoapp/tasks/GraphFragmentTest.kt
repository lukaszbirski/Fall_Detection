package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.GraphFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class GraphFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launchGraphFragment_allElementsAreDisplayed() {
        launchFragmentInHiltContainer<GraphFragment> {}

        onView(withId(R.id.accelerationTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.chart)).check(matches(isDisplayed()))
        onView(withId(R.id.startBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.stopBtn)).check(matches(isDisplayed()))
    }
}
