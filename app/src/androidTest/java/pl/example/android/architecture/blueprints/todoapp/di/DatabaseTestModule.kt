package pl.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import androidx.room.Room
import pl.example.android.architecture.blueprints.todoapp.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Singleton
    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
}
