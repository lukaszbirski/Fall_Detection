package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.components.interfaces.FallDetector
import com.example.android.architecture.blueprints.todoapp.fakes.LocationTrackerFake
import com.example.android.architecture.blueprints.todoapp.fakes.SensorFake
import com.example.android.architecture.blueprints.todoapp.fakes.SignalFake
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.GraphFragment
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.HomeFragment
import com.example.android.architecture.blueprints.todoapp.presentation.viewmodel.GraphViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * End-to-End tests for the GraphFragment.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class GraphFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fallDetector: FallDetector

    private val signal = SignalFake()

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun falling_navigateToCounterScreen(): Unit = runBlocking {
        launchFragmentInHiltContainer<GraphFragment> {
            viewModel = GraphViewModel(
                locationTracker = LocationTrackerFake(),
                sensor = SensorFake(
                    fallDetector = fallDetector,
                    signal = signal.signalFall
                )
            )
        }

        // press start button
        onView(withId(R.id.startBtn)).perform(click())
        // wait 15 sec to detect fall
        delay(15000)
        // checks if counter screen is displayed
        onView(withId(R.id.fallDetectedTextView)).check(matches(isDisplayed()))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun detectFallInHomeScreen_navigateToCounterScreen(): Unit = runBlocking {
        launchFragmentInHiltContainer<GraphFragment> {
            viewModel = GraphViewModel(
                locationTracker = LocationTrackerFake(),
                sensor = SensorFake(
                    fallDetector = fallDetector,
                    signal = signal.signalFall
                )
            )
        }

        // press start button
        onView(withId(R.id.startBtn)).perform(click())
        // wait 1 sec
        delay(1000)
        // move to home screen
        launchFragmentInHiltContainer<HomeFragment> {}
        // wait 15 sec to detect fall
        delay(15000)
        // checks if counter screen is displayed
        onView(withId(R.id.fallDetectedTextView)).check(matches(isDisplayed()))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun walking_doNotNavigateToCounterScreen(): Unit = runBlocking {
        launchFragmentInHiltContainer<GraphFragment> {
            viewModel = GraphViewModel(
                locationTracker = LocationTrackerFake(),
                sensor = SensorFake(
                    fallDetector = fallDetector,
                    signal = signal.signalWalking
                )
            )
        }

        // press start button
        onView(withId(R.id.startBtn)).perform(click())
        // wait 15 sec to detect fall
        delay(15000)
        // checks if graph screen is still displayed
        onView(withId(R.id.accelerationTitleTextView)).check(matches(isDisplayed()))
    }
}
