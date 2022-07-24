package com.example.android.architecture.blueprints.todoapp.fakes

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.android.architecture.blueprints.todoapp.components.interfaces.FallDetector
import com.example.android.architecture.blueprints.todoapp.components.interfaces.Sensor
import com.example.android.architecture.blueprints.todoapp.model.Acceleration
import com.example.android.architecture.blueprints.todoapp.other.Constants
import javax.inject.Inject

class SensorFake @Inject constructor(
    private val fallDetector: FallDetector,
    private val signal: List<Acceleration>
) : Sensor {

    private lateinit var mainHandler: Handler

    private val acceleration: MutableState<Acceleration?> = mutableStateOf(null)
    private var index = 0

    private val stabilize = object : Runnable {
        override fun run() {
            if (index < signal.size) {
                fallDetector.detectFall(signal[index])
                index++
            }
            mainHandler.postDelayed(this, Constants.INTERVAL_MILISEC.toLong())
        }
    }

    override fun initiateSensor(context: Context) {
        runStabilizer()
    }

    override fun stopMeasurement() {
        stopStabilizer()
    }

    override fun getMutableAcceleration() = acceleration

    private fun runStabilizer() {
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(stabilize)
    }

    private fun stopStabilizer() {
        mainHandler.removeCallbacks(stabilize)
    }
}
