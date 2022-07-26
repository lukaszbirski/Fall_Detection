package pl.birski.falldetector.architecture.blueprints.todoapp.repository

import pl.birski.falldetector.architecture.blueprints.todoapp.data.dao.ContactDao
import pl.birski.falldetector.architecture.blueprints.todoapp.model.Contact
import pl.birski.falldetector.architecture.blueprints.todoapp.model.util.ContactMapper
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val mapper: ContactMapper
) : Repository {

    override suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(mapper.mapFromDomainModel(contact))
    }

    override suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(mapper.mapFromDomainModel(contact))
    }

    override suspend fun observeAllContacts() =
        mapper.mapToDomainModelList(contactDao.getAllContacts())
}
