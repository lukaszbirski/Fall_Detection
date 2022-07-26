package pl.example.android.architecture.blueprints.todoapp.model.util

import pl.example.android.architecture.blueprints.todoapp.data.model.ContactEntity
import pl.example.android.architecture.blueprints.todoapp.data.source.AppDatabaseFake
import pl.example.android.architecture.blueprints.todoapp.model.Contact
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the implementation of [ContactMapper].
 */
class ContactMapperTest {

    private val contactsEntity = AppDatabaseFake().contacts

    // system in test
    private lateinit var mapper: ContactMapper

    @Before
    fun setup() {
        mapper = ContactMapper()
    }

    @Test
    fun `map list of contactEntity to list of contacts`() {
        val contacts = mapper.mapToDomainModelList(contactsEntity)

        // confirm that list is not empty
        assert(contacts.isNotEmpty())

        // confirm they are actually Contact objects
        assert(contacts.get(index = 0) is Contact)
    }

    @Test
    fun `map contactEntity to contact`() {
        val contacts = mapper.mapToDomainModel(contactsEntity[0])

        // confirm that mapped object is Contact type
        assert(contacts is Contact)
    }

    @Test
    fun `map contact to contact entity`() {
        val contacts = mapper.mapToDomainModel(contactsEntity[0])

        // confirm that mapped object is Contact type
        assert(contacts is Contact)

        val contactEntity = mapper.mapFromDomainModel(contacts)

        // confirm that mapped object is ContactEntity type
        assert(contactEntity is ContactEntity)
    }
}
