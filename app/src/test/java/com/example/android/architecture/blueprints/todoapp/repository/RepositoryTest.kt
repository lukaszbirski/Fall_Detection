package com.example.android.architecture.blueprints.todoapp.repository

import com.example.android.architecture.blueprints.todoapp.data.source.AppDatabaseFake
import com.example.android.architecture.blueprints.todoapp.data.source.ContactDaoFake
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the implementation of [Repository].
 */
class RepositoryTest {

    private val appDatabase = AppDatabaseFake()

    // system in test
    private lateinit var repository: Repository

    // dependencies
    private lateinit var contactDao: ContactDaoFake
    private lateinit var mapper: ContactMapper

    @Before
    fun setup() {
        contactDao = ContactDaoFake(appDatabase)
        mapper = ContactMapper()
        repository = RepositoryImpl(
            mapper = mapper,
            contactDao = contactDao
        )
    }

    @Test
    fun `get all contacts from database`(): Unit = runBlocking {
        val contacts = repository.observeAllContacts()

        // confirm that we received data from database
        assert(contacts.isNotEmpty())

        // confirm they are actually Contact objects
        assert(contacts.get(index = 0) is Contact)
    }

    @Test
    fun `add contact to database`(): Unit = runBlocking {
        // want to add element to database
        val newContact = Contact(
            id = 3,
            name = "Robert",
            surname = "Johnsson",
            prefix = "+15",
            number = "121212121"
        )

        val contacts = repository.observeAllContacts().toList()

        // confirm that database do not contain element
        assert(newContact !in contacts)

        repository.insertContact(newContact)

        val contactList = repository.observeAllContacts().toList()

        // confirm that database contain element after it was added to database
        assert(newContact in contactList)
    }

    @Test
    fun `remove contact from database`(): Unit = runBlocking {
        // want to remove first element from database
        val contactToRemove = mapper.mapToDomainModel(appDatabase.contacts[0])

        repository.deleteContact(contact = contactToRemove)

        val contacts = repository.observeAllContacts().toList()

        // confirm that we removed element from database
        assert(contactToRemove !in contacts)
    }
}
