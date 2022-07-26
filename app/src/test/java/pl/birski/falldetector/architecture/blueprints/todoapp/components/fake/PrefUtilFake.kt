package pl.birski.falldetector.architecture.blueprints.todoapp.components.fake

import pl.birski.falldetector.architecture.blueprints.todoapp.other.PrefUtil
import pl.birski.falldetector.architecture.blueprints.todoapp.service.enum.Algorithms

class PrefUtilFake : PrefUtil {

    private var algorithm = Algorithms.FIRST

    override fun getTimerLength() = 2L

    override fun getDetectionAlgorithm() = algorithm

    override fun isSendingMessageAllowed() = false

    fun setAlgorithm(algorithm: Algorithms) {
        this.algorithm = algorithm
    }
}
