package com.example.android.architecture.blueprints.todoapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.MyFakeRepository
import com.example.android.architecture.blueprints.todoapp.presentation.viewmodel.ContactsViewModel
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setup() {
        viewModel = ContactsViewModel(MyFakeRepository())
    }

    @Test
    fun `get all contacts`() = runTest {
        viewModel.getAllContacts()
        Thread.sleep(1000L)
        val contacts = viewModel.contacts.value?.toList() ?: listOf()

        assert(contacts.isNotEmpty())
    }

//    @Test
//    fun `insert contact`() {
//        val contact = Contact(
//            id = 3,
//            name = "Robert",
//            surname = "Redford",
//            prefix = "+14",
//            number = "123456789"
//        )
//
//        viewModel.setContactData(Contact())
//        viewModel.addContact()
//
//        val contacts = viewModel.contacts.value?.toList() ?: listOf()

//        assert(contacts.isNotEmpty())
//
//        assert(contact in contacts)
//    }
}
