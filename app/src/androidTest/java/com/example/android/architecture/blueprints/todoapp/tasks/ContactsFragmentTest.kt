package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.ContactsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * End-to-End tests for the ContactsFragment.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class ContactsFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launchContactsFragment_allElementsAreDisplayed() {
        launchFragmentInHiltContainer<ContactsFragment> {}

        // checks if all elements are displayed
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.addItemTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.contactsRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.addContactButton)).check(matches(isDisplayed()))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addContact_newElementsIsDisplayedInList() {
        launchFragmentInHiltContainer<ContactsFragment> {}

        // check if button is displayed
        onView(withId(R.id.addContactButton)).check(matches(isDisplayed()))
        // presses button
        onView(withId(R.id.addContactButton)).perform(click())
        // checks if dialog is displayed
        onView(isRoot()).inRoot(isDialog()).check(matches(isDisplayed()))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types name
        onView(withId(R.id.nameEditText)).perform(typeText("Jan"))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types surname
        onView(withId(R.id.surnameEditText)).perform(typeText("Nowak"))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types prefix
        onView(withId(R.id.prefixEditText)).perform(typeText("42"))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types phone number
        onView(withId(R.id.phoneEditText)).perform(typeText("123456789"))
        // checks if both buttons are enabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(isEnabled()))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // clicks add button
        onView(withText(R.string.contact_dialog_add_text)).perform(click())
    }
}
