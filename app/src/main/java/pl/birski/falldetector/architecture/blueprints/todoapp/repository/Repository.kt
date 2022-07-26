package pl.birski.falldetector.architecture.blueprints.todoapp.repository

import pl.birski.falldetector.architecture.blueprints.todoapp.model.Contact

interface Repository {

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun observeAllContacts(): List<Contact>
}
