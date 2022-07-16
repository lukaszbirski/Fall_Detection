package com.example.android.architecture.blueprints.todoapp.components.interfaces

import com.example.android.architecture.blueprints.todoapp.model.HighPassFilterData

interface Filter {

    fun lowPassFilter(input: FloatArray, output: FloatArray?, alpha: Float): FloatArray

    fun highPassFilter(
        input: FloatArray,
        hpfData: HighPassFilterData,
        alpha: Float
    ): HighPassFilterData

    fun calculateAlpha(cutOffFrequency: Double, frequency: Double): Float
}
