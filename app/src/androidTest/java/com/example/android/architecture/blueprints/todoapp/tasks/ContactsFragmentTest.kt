package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.ContactsFragment
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.adapter.ContactAdapter
import com.example.android.architecture.blueprints.todoapp.presentation.viewmodel.ContactsViewModel
import com.example.android.architecture.blueprints.todoapp.repository.Repository
import com.example.android.architecture.blueprints.todoapp.util.CustomHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * End-to-End tests for the ContactsFragment.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class ContactsFragmentTest {

    private val contact = Contact(
        id = null,
        name = "Jan Emanuel",
        surname = "Nowak",
        prefix = "42",
        number = "123456789"
    )

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: Repository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun launchContactsFragment_allElementsAreDisplayed() {
        launchFragmentInHiltContainer<ContactsFragment> {
            viewModel = ContactsViewModel(repository)
        }

        // checks if all elements are displayed
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.addItemTitleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.contactsRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.addContactButton)).check(matches(isDisplayed()))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addingContactClicksCancel_newElementsIsNotAddedToList() {
        launchFragmentInHiltContainer<ContactsFragment> {
            viewModel = ContactsViewModel(repository)
        }

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
        onView(withId(R.id.nameEditText)).perform(typeText(contact.name))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // clicks cancel
        onView(withText(R.string.contact_dialog_cancel_text)).perform(click())

        launchFragmentInHiltContainer<ContactsFragment> {}

        // checks if element is not contact is displayed on the list
        onView(withId(R.id.contactsRecycler))
            .check(matches(not(hasDescendant(withText("${contact.name} ${contact.surname}")))))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteExistingContact_newElementsIsRemovedFromList() {
        // makes sure that some contact is in place
        whenAddingContact_newElementsIsDisplayedInList()

        launchFragmentInHiltContainer<ContactsFragment> {
            viewModel = ContactsViewModel(repository)
        }

        // clicks on remove button in first element of the list
        onView(withId(R.id.contactsRecycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ContactAdapter.ContactViewHolder>(
                0,
                CustomHelper.clickChildViewWithId(R.id.removeContact)
            )
        )
        // checks if dialog is displayed
        onView(isRoot()).inRoot(isDialog()).check(matches(isDisplayed()))
        // checks if elements of dialog are displayed
        val message = String.format(
            CustomHelper.getResourceString(R.string.remove_contact_dialog_message_text),
            contact.name,
            contact.surname
        )
        onView(withText(message)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_cancel_text)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_remove_text)).check(matches(isDisplayed()))
        // clicks remove button
        onView(withText(R.string.remove_contact_dialog_remove_text)).perform(click())

        launchFragmentInHiltContainer<ContactsFragment> {}

        // checks if removed element is not displayed on the list
        onView(withId(R.id.contactsRecycler))
            .check(matches(not(hasDescendant(withText("${contact.name} ${contact.surname}")))))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun clickCancelWhenDeletingExistingContact_contactIsNotRemoved() {
        // makes sure that some contact is in place
        whenAddingContact_newElementsIsDisplayedInList()

        launchFragmentInHiltContainer<ContactsFragment> {}

        // clicks on remove button in first element of the list
        onView(withId(R.id.contactsRecycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ContactAdapter.ContactViewHolder>(
                0,
                CustomHelper.clickChildViewWithId(R.id.removeContact)
            )
        )
        // checks if dialog is displayed
        onView(isRoot()).inRoot(isDialog()).check(matches(isDisplayed()))
        // checks if elements of dialog are displayed
        val message = String.format(
            CustomHelper.getResourceString(R.string.remove_contact_dialog_message_text),
            contact.name,
            contact.surname
        )
        onView(withText(message)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_title_text)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_cancel_text)).check(matches(isDisplayed()))
        onView(withText(R.string.remove_contact_dialog_remove_text)).check(matches(isDisplayed()))
        // clicks remove button
        onView(withText(R.string.remove_contact_dialog_cancel_text)).perform(click())

        launchFragmentInHiltContainer<ContactsFragment> {
            viewModel = ContactsViewModel(repository)
        }

        // checks if removed element is not displayed on the list
        onView(withId(R.id.contactsRecycler))
            .check(matches(hasDescendant(withText("${contact.name} ${contact.surname}"))))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenAddingContact_newElementsIsDisplayedInList() {
        launchFragmentInHiltContainer<ContactsFragment> {
            viewModel = ContactsViewModel(repository)
        }

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
        onView(withId(R.id.nameEditText)).perform(typeText(contact.name))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types surname
        onView(withId(R.id.surnameEditText)).perform(typeText(contact.surname))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types prefix
        onView(withId(R.id.prefixEditText)).perform(typeText(contact.prefix))
        // checks if cancel button is enabled and add button disabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(not(isEnabled())))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        // types phone number
        onView(withId(R.id.phoneEditText)).perform(typeText(contact.number))
        // checks if both buttons are enabled
        onView(withText(R.string.contact_dialog_add_text)).check(matches(isEnabled()))
        onView(withText(R.string.contact_dialog_cancel_text)).check(matches(isEnabled()))
        onView(withText(R.string.contact_dialog_add_text)).perform(click())

        launchFragmentInHiltContainer<ContactsFragment> {}

        // checks if added contact is displayed on the list
        onView(withId(R.id.contactsRecycler))
            .perform(RecyclerViewActions.scrollToPosition<ContactAdapter.ContactViewHolder>(0))
            .check(matches(hasDescendant(withText("${contact.name} ${contact.surname}"))))
    }
}
