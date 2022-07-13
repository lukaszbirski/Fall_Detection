package com.example.android.architecture.blueprints.todoapp

import com.example.android.architecture.blueprints.todoapp.model.Contact

interface ContactAdapterListener {

    fun removeContact(contact: Contact)
}
