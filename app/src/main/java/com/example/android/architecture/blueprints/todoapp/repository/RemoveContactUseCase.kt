package com.example.android.architecture.blueprints.todoapp.repository

import com.example.android.architecture.blueprints.todoapp.data.dao.ContactDao
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper

class RemoveContactUseCase(
    private val contactDao: ContactDao,
    private val mapper: ContactMapper
) {
    suspend fun execute(contact: Contact) {
        return contactDao.deleteContact(mapper.mapFromDomainModel(contact))
    }
}
