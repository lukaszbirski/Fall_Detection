package pl.birski.android.architecture.blueprints.todoapp.di

import android.content.Context
import pl.birski.android.architecture.blueprints.todoapp.components.implementations.FallDetectorImpl
import pl.birski.android.architecture.blueprints.todoapp.components.implementations.FilterImpl
import pl.birski.android.architecture.blueprints.todoapp.components.implementations.SensorImpl
import pl.birski.android.architecture.blueprints.todoapp.components.interfaces.FallDetector
import pl.birski.android.architecture.blueprints.todoapp.components.interfaces.Filter
import pl.birski.android.architecture.blueprints.todoapp.components.interfaces.Sensor
import pl.birski.android.architecture.blueprints.todoapp.other.PrefUtil
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
        filter: Filter,
        prefUtil: PrefUtil
    ): FallDetector = FallDetectorImpl(context, filter, prefUtil)

    @Singleton
    @Provides
    fun provideFilter(): Filter = FilterImpl()
}
