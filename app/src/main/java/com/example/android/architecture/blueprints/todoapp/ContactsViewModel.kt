package com.example.android.architecture.blueprints.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var newContact = Contact()

    private val _contacts = MutableLiveData<ArrayList<Contact>>()
    val contacts: LiveData<ArrayList<Contact>> get() = _contacts

    init {
//        getAllContacts()
    }

//    fun setContactData(contact: Contact) {
//        this.newContact = contact
//    }
//
//    fun getContact() = newContact
//
//    fun addContact() {
//        addPlusToPrefix()
//        viewModelScope.launch(Dispatchers.IO) {
//            useCaseFactory.addDriverUseCase.execute(newContact)
//            getAllContacts()
//        }
//    }
//
//    private fun addPlusToPrefix() {
//        newContact = newContact.copy(prefix = "+${newContact.prefix}")
//    }
//
//    private fun getAllContacts() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = useCaseFactory.getAllContactsUseCase.execute() as ArrayList
//            Timber.d("Got ${result.size} contacts from database")
//            _contacts.postValue(result)
//        }
//    }
//
//    fun removeContact(contact: Contact) {
//        viewModelScope.launch(Dispatchers.IO) {
//            useCaseFactory.removeContactUseCase.execute(contact)
//            getAllContacts()
//        }
//    }
//
//    fun enableButton() =
//        newContact.name.isNotBlank() &&
//                newContact.surname.isNotBlank() &&
//                newContact.prefix.isNotBlank() &&
//                newContact.number.isNotBlank()
}
