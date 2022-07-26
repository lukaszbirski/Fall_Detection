package pl.birski.falldetector.architecture.blueprints.todoapp.di

import android.content.Context
import pl.birski.falldetector.architecture.blueprints.todoapp.components.implementations.LocationTrackerImpl
import pl.birski.falldetector.architecture.blueprints.todoapp.components.implementations.MessageSenderImpl
import pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces.LocationTracker
import pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces.MessageSender
import pl.birski.falldetector.architecture.blueprints.todoapp.other.PrefUtil
import pl.birski.falldetector.architecture.blueprints.todoapp.other.PrefUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocationTracker(@ApplicationContext context: Context): LocationTracker =
        LocationTrackerImpl(context)

    @Singleton
    @Provides
    fun providePrefUtil(@ApplicationContext app: Context): PrefUtil = PrefUtilImpl(app)

    @Singleton
    @Provides
    fun provideMessageSender(
        @ApplicationContext app: Context,
        locationTracker: LocationTracker
    ): MessageSender =
        MessageSenderImpl(app, locationTracker)
}
