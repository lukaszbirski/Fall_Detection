package com.example.android.architecture.blueprints.todoapp.di

import com.example.android.architecture.blueprints.todoapp.data.AppDatabase
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import com.example.android.architecture.blueprints.todoapp.repository.AddContactUseCase
import com.example.android.architecture.blueprints.todoapp.repository.GetAllContactsUseCase
import com.example.android.architecture.blueprints.todoapp.repository.RemoveContactUseCase
import com.example.android.architecture.blueprints.todoapp.repository.Repository
import com.example.android.architecture.blueprints.todoapp.repository.RepositoryImpl
import com.example.android.architecture.blueprints.todoapp.repository.UseCaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideRepository(
        database: AppDatabase,
        mapper: ContactMapper
    ): Repository = RepositoryImpl(database.contactDao(), mapper)

    @Singleton
    @Provides
    fun provideUseCaseFactory(
        addDriverUseCase: AddContactUseCase,
        getAllContactsUseCase: GetAllContactsUseCase,
        removeContactUseCase: RemoveContactUseCase
    ): UseCaseFactory {
        return UseCaseFactory(
            addDriverUseCase = addDriverUseCase,
            getAllContactsUseCase = getAllContactsUseCase,
            removeContactUseCase = removeContactUseCase
        )
    }

    @Singleton
    @Provides
    fun provideContactMapper(): ContactMapper {
        return ContactMapper()
    }

    @Singleton
    @Provides
    fun provideAddContactUseCase(
        database: AppDatabase,
        mapper: ContactMapper
    ): AddContactUseCase {
        return AddContactUseCase(
            contactDao = database.contactDao(),
            mapper = mapper
        )
    }

    @Singleton
    @Provides
    fun provideGetAllContactsUseCase(
        database: AppDatabase,
        mapper: ContactMapper
    ): GetAllContactsUseCase {
        return GetAllContactsUseCase(
            contactDao = database.contactDao(),
            mapper = mapper
        )
    }

    @Singleton
    @Provides
    fun provideRemoveContactUSeCase(
        database: AppDatabase,
        mapper: ContactMapper
    ): RemoveContactUseCase {
        return RemoveContactUseCase(
            contactDao = database.contactDao(),
            mapper = mapper
        )
    }
}
