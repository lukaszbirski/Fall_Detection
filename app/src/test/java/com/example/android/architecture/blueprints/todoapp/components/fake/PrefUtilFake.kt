package com.example.android.architecture.blueprints.todoapp.components.fake

import com.example.android.architecture.blueprints.todoapp.other.PrefUtil
import com.example.android.architecture.blueprints.todoapp.presentation.service.enum.Algorithms

class PrefUtilFake : PrefUtil {

    private var algorithm = Algorithms.FIRST

    override fun getTimerLength() = 2L

    override fun getDetectionAlgorithm() = algorithm

    override fun isSendingMessageAllowed() = false

    fun setAlgorithm(algorithm: Algorithms) {
        this.algorithm = algorithm
    }
}
