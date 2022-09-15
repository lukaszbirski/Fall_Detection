package pl.birski.falldetector.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.birski.falldetector.components.implementations.FallDetectorImpl
import pl.birski.falldetector.components.implementations.FilterImpl
import pl.birski.falldetector.components.implementations.SensorImpl
import pl.birski.falldetector.components.interfaces.FallDetector
import pl.birski.falldetector.components.interfaces.Filter
import pl.birski.falldetector.components.interfaces.Sensor
import pl.birski.falldetector.other.PrefUtil
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
