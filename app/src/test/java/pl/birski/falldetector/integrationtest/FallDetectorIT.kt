package pl.birski.falldetector.integrationtest

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.internal.DoNotInstrument
import pl.birski.falldetector.components.fake.FallDetectorDataFake
import pl.birski.falldetector.components.fake.PrefUtilFake
import pl.birski.falldetector.components.implementations.FallDetectorImpl
import pl.birski.falldetector.components.implementations.FilterImpl
import pl.birski.falldetector.components.interfaces.Filter
import pl.birski.falldetector.service.enum.Algorithms

/**
 * Integration test for the implementation of [FallDetectorImpl].
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@DoNotInstrument
class FallDetectorIT {

    private val data = FallDetectorDataFake()

    // system in test
    private lateinit var fallDetector: FallDetectorImpl

    private lateinit var filter: Filter
    private lateinit var prefUtil: PrefUtilFake

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        filter = FilterImpl()
        prefUtil = PrefUtilFake()
        fallDetector = FallDetectorImpl(context, filter, prefUtil)
    }

    @Test
    fun `detect fall for algorithm I when falling`() {
        prefUtil.setAlgorithm(Algorithms.FIRST)

        data.signalFall.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(true, fallDetector.isLyingPostureDetected)
    }

    @Test
    fun `detect fall for algorithm II when falling`() {
        prefUtil.setAlgorithm(Algorithms.SECOND)

        data.signalFall.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(true, fallDetector.isLyingPostureDetected)
    }

    @Test
    fun `detect fall for algorithm III when falling`() {
        prefUtil.setAlgorithm(Algorithms.THIRD)

        data.signalFall.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(true, fallDetector.isLyingPostureDetected)
    }

    @Test
    fun `do not detect fall for algorithm I when walking`() {
        prefUtil.setAlgorithm(Algorithms.FIRST)

        data.signalWalking.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(false, fallDetector.isLyingPostureDetected)
    }

    @Test
    fun `do not detect fall for algorithm II when walking`() {
        prefUtil.setAlgorithm(Algorithms.SECOND)

        data.signalWalking.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(false, fallDetector.isLyingPostureDetected)
    }

    @Test
    fun `do not detect fall for algorithm III when walking`() {
        prefUtil.setAlgorithm(Algorithms.THIRD)

        data.signalWalking.forEach {
            fallDetector.detectFall(it)
        }

        assertEquals(false, fallDetector.isLyingPostureDetected)
    }
}
