package pl.birski.android.architecture.blueprints.todoapp.presentation.fragment.adapter

import pl.birski.android.architecture.blueprints.todoapp.model.Contact

interface ContactAdapterListener {

    fun removeContact(contact: Contact)
}
