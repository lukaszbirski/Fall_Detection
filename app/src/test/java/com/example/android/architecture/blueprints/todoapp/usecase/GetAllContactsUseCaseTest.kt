package com.example.android.architecture.blueprints.todoapp.usecase

import com.example.android.architecture.blueprints.todoapp.data.source.AppDatabaseFake
import com.example.android.architecture.blueprints.todoapp.data.source.ContactDaoFake
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetAllContactsUseCaseTest {

    private val appDatabase = AppDatabaseFake()

    // system in test
    private lateinit var getAllContactsUseCase: GetAllContactsUseCase

    // dependencies
    private lateinit var contactDao: ContactDaoFake
    private lateinit var mapper: ContactMapper

    @Before
    fun setup() {
        contactDao = ContactDaoFake(appDatabase)

        mapper = ContactMapper()

        getAllContactsUseCase = GetAllContactsUseCase(
            mapper = mapper,
            contactDao = contactDao
        )
    }

    @Test
    fun `get all contacts from database`(): Unit = runBlocking {
        val contacts = getAllContactsUseCase.execute().toList()

        // confirm that we received data from database
        assert(contacts.isNotEmpty())

        // confirm they are actually Contact objects
        assert(contacts.get(index = 0) is Contact)
    }

    @After
    fun tearDown() { }
}
