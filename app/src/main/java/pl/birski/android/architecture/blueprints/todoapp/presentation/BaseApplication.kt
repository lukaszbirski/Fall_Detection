package pl.birski.android.architecture.blueprints.todoapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (pl.birski.android.architecture.blueprints.todoapp.BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
