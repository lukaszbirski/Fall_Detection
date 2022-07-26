package pl.birski.falldetector.architecture.blueprints.todoapp.other

import pl.birski.falldetector.architecture.blueprints.todoapp.service.enum.Algorithms

interface PrefUtil {

    fun getTimerLength(): Long

    fun getDetectionAlgorithm(): Algorithms

    fun isSendingMessageAllowed(): Boolean
}
