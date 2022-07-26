package pl.example.android.architecture.blueprints.todoapp.tasks

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
import pl.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import pl.example.android.architecture.blueprints.todoapp.presentation.fragment.HomeFragment

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

        onView(withText("TEST")).check(
            matches(isDisplayed())
        )
    }
}
