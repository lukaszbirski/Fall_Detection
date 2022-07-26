package pl.birski.falldetector.architecture.blueprints.todoapp.components

import pl.birski.falldetector.architecture.blueprints.todoapp.components.implementations.FilterImpl
import pl.birski.falldetector.architecture.blueprints.todoapp.model.HighPassFilterData
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

/**
 * Unit tests for the implementation of [FilterImpl].
 */
class FilterTest {

    // system in test
    private val filter = FilterImpl()

    private var alpha = 0.5f

    private lateinit var accelerationFloatArray: FloatArray
    private lateinit var lowPassFilterData: FloatArray
    private lateinit var highPassFilterData: HighPassFilterData

    @Before
    fun setup() {
        accelerationFloatArray = floatArrayOf(-0.548f, 0.257f, 0.756f)

        lowPassFilterData = floatArrayOf(0.0f, 0.0f, 0.0f)

        highPassFilterData = HighPassFilterData(
            floatArrayOf(0.0f, 0.0f, 0.0f),
            floatArrayOf(0.0f, 0.0f, 0.0f)
        )
    }

    @Test
    fun `test LowPassFilter`() {
        // during each iteration filtered signal should increase in half from previous value to signal
        // value (accelerationFloatArray)
        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.274f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.128f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.378f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.410f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.192f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.567f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.479f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.224f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.661f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.513f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.240f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.708f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.530f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.248f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.732f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.539f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.252f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.744f, lowPassFilterData[2], 0.001f)

        lowPassFilterData = filter.lowPassFilter(accelerationFloatArray, lowPassFilterData, alpha)
        Assertions.assertEquals(-0.543f, lowPassFilterData[0], 0.001f)
        Assertions.assertEquals(0.254f, lowPassFilterData[1], 0.001f)
        Assertions.assertEquals(0.750f, lowPassFilterData[2], 0.001f)
    }

    @Test
    fun `test HighPassFilter`() {
        // during each iteration gravitational component should increase in half from previous
        // value to signal value (accelerationFloatArray)
        // whereas, since signal is const acceleration component should decrease to 0
        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.274f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.128f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.378f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.274f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.128f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.378f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.137f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.064f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.189f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.410f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.192f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.567f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.068f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.032f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.094f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.479f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.224f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.661f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.034f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.016f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.047f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.513f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.240f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.708f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.017f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.008f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.023f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.530f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.248f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.732f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.008f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.004f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.011f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.539f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.252f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.744f, highPassFilterData.gravity[2], 0.001f)

        highPassFilterData = filter.highPassFilter(
            accelerationFloatArray,
            highPassFilterData,
            alpha
        )
        Assertions.assertEquals(-0.004f, highPassFilterData.acceleration[0], 0.001f)
        Assertions.assertEquals(0.002f, highPassFilterData.acceleration[1], 0.001f)
        Assertions.assertEquals(0.005f, highPassFilterData.acceleration[2], 0.001f)
        Assertions.assertEquals(-0.543f, highPassFilterData.gravity[0], 0.001f)
        Assertions.assertEquals(0.254f, highPassFilterData.gravity[1], 0.001f)
        Assertions.assertEquals(0.750f, highPassFilterData.gravity[2], 0.001f)
    }

    @Test
    fun `test if calculate alpha correctly`() {
        Assertions.assertEquals(
            0.996f,
            filter.calculateAlpha(0.25, 50.0),
            0.001f
        )

        Assertions.assertEquals(
            0.991f,
            filter.calculateAlpha(0.5, 50.0),
            0.001f
        )

        Assertions.assertEquals(
            0.980f,
            filter.calculateAlpha(1.0, 50.0),
            0.001f
        )

        Assertions.assertEquals(
            0.910f,
            filter.calculateAlpha(1.0, 10.0),
            0.001f
        )

        Assertions.assertEquals(
            0.500f,
            filter.calculateAlpha(10.0, 10.0),
            0.001f
        )
    }
}
