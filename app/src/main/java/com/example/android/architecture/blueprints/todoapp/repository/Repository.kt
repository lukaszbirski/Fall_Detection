package com.example.android.architecture.blueprints.todoapp.repository

import com.example.android.architecture.blueprints.todoapp.model.Contact

interface Repository {

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun observeAllContacts(): List<Contact>
}
