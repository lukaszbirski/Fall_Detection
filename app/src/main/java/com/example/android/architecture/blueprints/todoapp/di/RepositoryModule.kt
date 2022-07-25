package com.example.android.architecture.blueprints.todoapp.di

import com.example.android.architecture.blueprints.todoapp.data.AppDatabase
import com.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import com.example.android.architecture.blueprints.todoapp.repository.Repository
import com.example.android.architecture.blueprints.todoapp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        database: AppDatabase,
        mapper: ContactMapper
    ): Repository = RepositoryImpl(database.contactDao(), mapper)

    @Singleton
    @Provides
    fun provideContactMapper(): ContactMapper {
        return ContactMapper()
    }
}
