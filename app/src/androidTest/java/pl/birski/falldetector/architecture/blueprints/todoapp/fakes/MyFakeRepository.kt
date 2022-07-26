package pl.birski.falldetector.architecture.blueprints.todoapp.fakes

import pl.birski.falldetector.architecture.blueprints.todoapp.model.Contact
import pl.birski.falldetector.architecture.blueprints.todoapp.repository.Repository

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
