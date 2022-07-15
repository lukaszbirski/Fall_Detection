package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import com.example.android.architecture.blueprints.todoapp.components.implementations.LocationTrackerImpl
import com.example.android.architecture.blueprints.todoapp.components.interfaces.LocationTracker
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
}
