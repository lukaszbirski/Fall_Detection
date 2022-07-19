package com.example.android.architecture.blueprints.todoapp.repository

import com.example.android.architecture.blueprints.todoapp.data.source.AppDatabaseFake
import com.example.android.architecture.blueprints.todoapp.data.source.ContactDaoFake
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoveContactUseCaseTest {

    private val appDatabase = AppDatabaseFake()

    // system in test
    private lateinit var removeContactUseCase: RemoveContactUseCase

    // dependencies
    private lateinit var contactDao: ContactDaoFake
    private val mapper = ContactMapper()

    private lateinit var getAllContactsUseCase: GetAllContactsUseCase

    // want to remove first element from database
    private val contactToRemove = mapper.mapToDomainModel(appDatabase.contacts[0])

    @Before
    fun setup() {
        contactDao = ContactDaoFake(appDatabase)

        removeContactUseCase = RemoveContactUseCase(
            mapper = mapper,
            contactDao = contactDao
        )

        getAllContactsUseCase = GetAllContactsUseCase(
            mapper = mapper,
            contactDao = contactDao
        )
    }

    @Test
    fun `remove contact from database`(): Unit = runBlocking {
        removeContactUseCase.execute(contact = contactToRemove)

        val contacts = getAllContactsUseCase.execute().toList()

        // confirm that we removed element from database
        assert(contactToRemove !in contacts)
    }

    @After
    fun tearDown() { }
}
