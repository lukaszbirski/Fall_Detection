package com.example.android.architecture.blueprints.todoapp.usecase

import com.example.android.architecture.blueprints.todoapp.data.source.AppDatabaseFake
import com.example.android.architecture.blueprints.todoapp.data.source.ContactDaoFake
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AddContactUseCaseTest {

    private val appDatabase = AppDatabaseFake()

    // system in test
    private lateinit var addContactUseCase: AddContactUseCase

    // dependencies
    private lateinit var contactDao: ContactDaoFake
    private lateinit var mapper: ContactMapper

    private lateinit var getAllContactsUseCase: GetAllContactsUseCase

    // want to add element to database
    private val newContact = Contact(
        id = 3,
        name = "Robert",
        surname = "Johnsson",
        prefix = "+15",
        number = "121212121"
    )

    @Before
    fun setup() {
        contactDao = ContactDaoFake(appDatabase)

        mapper = ContactMapper()

        addContactUseCase = AddContactUseCase(
            mapper = mapper,
            contactDao = contactDao
        )

        getAllContactsUseCase = GetAllContactsUseCase(
            mapper = mapper,
            contactDao = contactDao
        )
    }

    @Test
    fun `add contact to database`(): Unit = runBlocking {
        val contacts = getAllContactsUseCase.execute().toList()

        // confirm that database do not contain element
        assert(newContact !in contacts)

        addContactUseCase.execute(newContact)

        val contactList = getAllContactsUseCase.execute().toList()

        // confirm that database contain element after it was added to database
        assert(newContact in contactList)
    }

    @After
    fun tearDown() {
    }
}
