package com.example.android.architecture.blueprints.todoapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.android.architecture.blueprints.todoapp.other.Constants
import com.example.android.architecture.blueprints.todoapp.other.Constants.NOTIFICATION_ID
import com.example.android.architecture.blueprints.todoapp.service.enum.ServiceActions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrackingService : LifecycleService() {

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ServiceActions.START_OR_RESUME.name -> {
                    Timber.d("Started or resumed service")
                    startForegroundService()
                }
                ServiceActions.STOP.name -> {
                    Timber.d("Stopped service")
                    stopForegroundService(intent)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            Constants.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        createNotificationChannel(notificationManager)
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopForegroundService(intent: Intent?) {
        this.stopService(intent)
    }
}
