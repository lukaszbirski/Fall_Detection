package com.example.android.architecture.blueprints.todoapp.presentation.fragment.adapter

import com.example.android.architecture.blueprints.todoapp.model.Contact

interface ContactAdapterListener {

    fun removeContact(contact: Contact)
}
