package pl.birski.falldetector.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import pl.birski.falldetector.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
