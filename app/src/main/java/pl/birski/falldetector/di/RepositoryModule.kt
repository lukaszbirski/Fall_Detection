package pl.birski.falldetector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.falldetector.data.AppDatabase
import pl.birski.falldetector.model.util.ContactMapper
import pl.birski.falldetector.repository.Repository
import pl.birski.falldetector.repository.RepositoryImpl
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
