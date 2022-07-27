package pl.birski.falldetector.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import pl.birski.falldetector.data.dao.ContactDao
import pl.birski.falldetector.data.model.ContactEntity
import pl.birski.falldetector.model.Contact
import pl.birski.falldetector.model.util.ContactMapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Unit tests of [ContactDao].
 */
@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ContactDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: AppDatabase
    private lateinit var dao: ContactDao
    private lateinit var mapper: ContactMapper

    private val firstContact = Contact(0, "Jan", "Nowak", "42", "123456789")
    private val secondContact = Contact(0, "Robert", "Smith", "23", "987654321")

    @Before
    fun setup() {
        mapper = ContactMapper()
        hiltRule.inject()
        dao = database.contactDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getAllContacts(): Unit = runBlocking {
        // inserts elements to db
        val firstEntity = mapper.mapFromDomainModel(firstContact)
        val secondEntity = mapper.mapFromDomainModel(secondContact)

        dao.insertContact(firstEntity)
        dao.insertContact(secondEntity)

        // gets elements
        val contacts = dao.getAllContacts()
        val resultList = mapper.mapToDomainModelList(contacts)

        // checks if proper elements were received from db
        assert(contacts is List<ContactEntity>)
        assert(firstContact in resultList)
    }

    @Test
    fun insertContact(): Unit = runBlocking {
        // checks if db is empty before inserting contact
        val emptyContactEntity = dao.getAllContacts()
        val emptyContact = mapper.mapToDomainModelList(emptyContactEntity)

        // bd should be empty
        assert(emptyContact.isEmpty())

        // inserts contact
        val entity = mapper.mapFromDomainModel(firstContact)
        dao.insertContact(entity)

        val contacts = dao.getAllContacts()
        val resultList = mapper.mapToDomainModelList(contacts)

        // checks if contact was inserted
        assert(contacts is List<ContactEntity>)
        assert(firstContact in resultList)
    }

    @Test
    fun deleteContact() = runBlocking {
        // checks if db is empty
        val emptyContactEntity = dao.getAllContacts()
        val emptyContact = mapper.mapToDomainModelList(emptyContactEntity)

        assert(emptyContact.isEmpty())

        // inserts contact
        val entity = mapper.mapFromDomainModel(firstContact)
        dao.insertContact(entity)

        val contacts = dao.getAllContacts()
        val resultList = mapper.mapToDomainModelList(contacts)

        // checks if contact is in db
        assert(firstContact in resultList)

        // deletes contact
        dao.deleteContact(entity)

        val emptyResultEntityList = dao.getAllContacts()
        val emptyResult = mapper.mapToDomainModelList(emptyResultEntityList)

        // checks if contact was removed
        assert(emptyResult.isEmpty())
    }
}
