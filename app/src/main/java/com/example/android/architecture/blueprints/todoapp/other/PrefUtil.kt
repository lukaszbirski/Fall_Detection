package com.example.android.architecture.blueprints.todoapp.other

import com.example.android.architecture.blueprints.todoapp.service.enum.Algorithms

interface PrefUtil {

    fun getTimerLength(): Long

    fun getDetectionAlgorithm(): Algorithms

    fun isSendingMessageAllowed(): Boolean
}
