package pl.example.android.architecture.blueprints.todoapp.presentation.fragment.adapter

import pl.example.android.architecture.blueprints.todoapp.model.Contact

interface ContactAdapterListener {

    fun removeContact(contact: Contact)
}
