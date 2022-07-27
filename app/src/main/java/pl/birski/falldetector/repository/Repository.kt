package pl.birski.falldetector.repository

import pl.birski.falldetector.model.Contact

interface Repository {

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun observeAllContacts(): List<Contact>
}
