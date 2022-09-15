package pl.birski.falldetector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import pl.birski.falldetector.fakes.MyFakeRepository
import pl.birski.falldetector.model.util.ContactMapper
import pl.birski.falldetector.repository.Repository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryTestModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = MyFakeRepository()

    @Singleton
    @Provides
    fun provideContactMapper(): ContactMapper {
        return ContactMapper()
    }
}
