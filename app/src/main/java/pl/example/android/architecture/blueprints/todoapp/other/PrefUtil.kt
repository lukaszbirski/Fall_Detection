package pl.example.android.architecture.blueprints.todoapp.other

import pl.example.android.architecture.blueprints.todoapp.service.enum.Algorithms

interface PrefUtil {

    fun getTimerLength(): Long

    fun getDetectionAlgorithm(): Algorithms

    fun isSendingMessageAllowed(): Boolean
}
