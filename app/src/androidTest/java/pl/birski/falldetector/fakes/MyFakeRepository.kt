package pl.birski.falldetector.fakes

import pl.birski.falldetector.model.Contact
import pl.birski.falldetector.repository.Repository

class MyFakeRepository : Repository {

    private val contacts = mutableListOf<Contact>()

    override suspend fun insertContact(contact: Contact) {
        contacts.add(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        contacts.remove(contact)
    }

    override suspend fun observeAllContacts() = contacts
}
