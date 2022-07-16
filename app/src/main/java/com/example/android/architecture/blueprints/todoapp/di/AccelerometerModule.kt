package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import com.example.android.architecture.blueprints.todoapp.components.implementations.FallDetectorImpl
import com.example.android.architecture.blueprints.todoapp.components.implementations.FilterImpl
import com.example.android.architecture.blueprints.todoapp.components.implementations.SensorImpl
import com.example.android.architecture.blueprints.todoapp.components.interfaces.FallDetector
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Filter
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Sensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccelerometerModule {

    @Singleton
    @Provides
    fun provideSensor(
        fallDetector: FallDetector
    ): Sensor = SensorImpl(fallDetector)

    @Singleton
    @Provides
    fun provideFallDetector(
        @ApplicationContext context: Context,
        filter: Filter
    ): FallDetector = FallDetectorImpl(context, filter)

    @Singleton
    @Provides
    fun provideFilter(): Filter = FilterImpl()
}
