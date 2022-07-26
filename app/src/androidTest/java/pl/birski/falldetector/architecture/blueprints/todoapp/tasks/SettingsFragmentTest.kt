package pl.birski.falldetector.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.birski.falldetector.architecture.blueprints.todoapp.R
import pl.birski.falldetector.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import pl.birski.falldetector.architecture.blueprints.todoapp.presentation.fragment.SettingsFragment

/**
 * End-to-End tests for the SettingsFragment.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class SettingsFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launchSettingsFragment_allElementsAreDisplayed() {
        launchFragmentInHiltContainer<SettingsFragment> {}

        onView(withText(R.string.preferences_category_timer_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.preferences_list_timer_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.preferences_category_detection_algorithm_title_text)).check(
            matches(isDisplayed())
        )
        onView(withText(R.string.preferences_list_algorithm_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.preferences_category_sending_notification_title_text)).check(
            matches(isDisplayed())
        )
        onView(withText(R.string.preferences_send_messages_title_text)).check(matches(isDisplayed()))
    }
}
