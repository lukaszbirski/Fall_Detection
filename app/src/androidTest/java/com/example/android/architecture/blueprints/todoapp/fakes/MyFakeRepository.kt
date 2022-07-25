package com.example.android.architecture.blueprints.todoapp.fakes

import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.repository.Repository

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
