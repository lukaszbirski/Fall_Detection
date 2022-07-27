package pl.birski.falldetector.data.source

import pl.birski.falldetector.data.dao.ContactDao
import pl.birski.falldetector.data.model.ContactEntity

class ContactDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : ContactDao {

    override suspend fun insertContact(contact: ContactEntity) {
        appDatabaseFake.contacts.add(contact)
    }

    override suspend fun getAllContacts(): List<ContactEntity> {
        return appDatabaseFake.contacts
    }

    override suspend fun deleteContact(contact: ContactEntity) {
        appDatabaseFake.contacts.removeIf { it.id == contact.id }
    }
}
