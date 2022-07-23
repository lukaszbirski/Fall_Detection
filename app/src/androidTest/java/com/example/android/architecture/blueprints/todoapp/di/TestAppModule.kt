package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.components.implementations.FallDetectorImpl
import com.example.android.architecture.blueprints.todoapp.components.interfaces.FallDetector
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Filter
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Sensor
import com.example.android.architecture.blueprints.todoapp.data.AppDatabase
import com.example.android.architecture.blueprints.todoapp.fakes.SensorFake
import com.example.android.architecture.blueprints.todoapp.other.PrefUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    @Named("test_sensor")
    fun provideSensor(fallDetector: FallDetector): Sensor = SensorFake(fallDetector)

    @Singleton
    @Provides
    @Named("test_falldetector")
    fun provideFallDetector(
        @ApplicationContext context: Context,
        filter: Filter,
        prefUtil: PrefUtil
    ): FallDetector = FallDetectorImpl(context, filter, prefUtil)
}
