package pl.birski.falldetector.components.fake

import pl.birski.falldetector.other.PrefUtil
import pl.birski.falldetector.service.enum.Algorithms

class PrefUtilFake : PrefUtil {

    private var algorithm = Algorithms.FIRST

    override fun getTimerLength() = 2L

    override fun getDetectionAlgorithm() = algorithm

    override fun isSendingMessageAllowed() = false

    fun setAlgorithm(algorithm: Algorithms) {
        this.algorithm = algorithm
    }
}
