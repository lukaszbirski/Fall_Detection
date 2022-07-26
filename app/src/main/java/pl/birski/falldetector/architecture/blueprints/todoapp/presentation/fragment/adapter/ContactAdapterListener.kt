package pl.birski.falldetector.architecture.blueprints.todoapp.presentation.fragment.adapter

import pl.birski.falldetector.architecture.blueprints.todoapp.model.Contact

interface ContactAdapterListener {

    fun removeContact(contact: Contact)
}
