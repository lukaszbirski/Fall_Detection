package pl.birski.falldetector.data.source

import pl.birski.falldetector.model.Contact
import pl.birski.falldetector.repository.Repository

class MyFakeRepository : Repository {

    private val contacts = mutableListOf(
        Contact(
            id = 0,
            name = "John",
            surname = "Smith",
            prefix = "+11",
            number = "123456789"
        ),
        Contact(
            id = 1,
            name = "Britney",
            surname = "Spears",
            prefix = "+12",
            number = "987654321"
        )
    )

    override suspend fun insertContact(contact: Contact) {
        contacts.add(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        contacts.remove(contact)
    }

    override suspend fun observeAllContacts() = contacts
}
