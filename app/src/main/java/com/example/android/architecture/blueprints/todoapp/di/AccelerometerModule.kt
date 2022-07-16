package com.example.android.architecture.blueprints.todoapp.di

import com.example.android.architecture.blueprints.todoapp.components.implementations.FilterImpl
import com.example.android.architecture.blueprints.todoapp.components.implementations.SensorImpl
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Filter
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Sensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccelerometerModule {

    @Singleton
    @Provides
    fun provideSensor(): Sensor = SensorImpl()

    @Singleton
    @Provides
    fun provideFilter(): Filter = FilterImpl()
}
