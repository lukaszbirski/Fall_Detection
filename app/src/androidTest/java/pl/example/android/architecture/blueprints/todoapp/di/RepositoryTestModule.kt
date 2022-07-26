package pl.example.android.architecture.blueprints.todoapp.di

import pl.example.android.architecture.blueprints.todoapp.fakes.MyFakeRepository
import pl.example.android.architecture.blueprints.todoapp.model.util.ContactMapper
import pl.example.android.architecture.blueprints.todoapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
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
