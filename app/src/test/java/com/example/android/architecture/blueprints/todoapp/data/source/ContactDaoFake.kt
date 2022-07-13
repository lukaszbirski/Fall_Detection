package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.dao.ContactDao
import com.example.android.architecture.blueprints.todoapp.data.model.ContactEntity

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
