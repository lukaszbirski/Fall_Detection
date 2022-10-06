package pl.birski.falldetector.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
import pl.birski.falldetector.R
import pl.birski.falldetector.launchFragmentInHiltContainer
import pl.birski.falldetector.presentation.fragment.HomeFragment

/**
 * End-to-End tests of [HomeFragment].
 * for proper tests running needs to allow permissions first
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launchHomeFragment_allElementsAreDisplayed() {
        launchFragmentInHiltContainer<HomeFragment> {}

        onView(withText(R.string.home_fragment_uni_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_faculty_text)).check(matches(isDisplayed()))
        onView(withId(R.id.logoImageVIew)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_thesis_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_course_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_author_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_supervisor_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_supervisor_name_text)).check(matches(isDisplayed()))
        onView(withText(R.string.home_fragment_city_date_text)).check(matches(isDisplayed()))
    }
}
