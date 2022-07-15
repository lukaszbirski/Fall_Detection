package com.example.android.architecture.blueprints.todoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.model.Contact
import com.example.android.architecture.blueprints.todoapp.usecase.UseCaseFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val useCaseFactory: UseCaseFactory
) : ViewModel() {

    private var newContact = Contact()

    private val _contacts = MutableLiveData<ArrayList<Contact>>()
    val contacts: LiveData<ArrayList<Contact>> get() = _contacts

    init {
        getAllContacts()
    }

    fun setContactData(contact: Contact) {
        this.newContact = contact
    }

    fun getContact() = newContact

    fun addContact() {
        addPlusToPrefix()
        viewModelScope.launch(Dispatchers.IO) {
            useCaseFactory.addDriverUseCase.execute(newContact)
            getAllContacts()
        }
    }

    private fun addPlusToPrefix() {
        newContact = newContact.copy(prefix = "+${newContact.prefix}")
    }

    private fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCaseFactory.getAllContactsUseCase.execute() as ArrayList
            Timber.d("Got ${result.size} contacts from database")
            _contacts.postValue(result)
        }
    }

    fun removeContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseFactory.removeContactUseCase.execute(contact)
            getAllContacts()
        }
    }

    fun enableButton() =
        newContact.name.isNotBlank() &&
            newContact.surname.isNotBlank() &&
            newContact.prefix.isNotBlank() &&
            newContact.number.isNotBlank()
}
