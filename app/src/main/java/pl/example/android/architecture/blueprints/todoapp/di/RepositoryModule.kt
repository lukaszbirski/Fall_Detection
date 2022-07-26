package pl.example.android.architecture.blueprints.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.example.android.architecture.blueprints.todoapp.data.AppDatabase
import pl.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import pl.example.android.architecture.blueprints.todoapp.repository.Repository
import pl.example.android.architecture.blueprints.todoapp.repository.RepositoryImpl
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
