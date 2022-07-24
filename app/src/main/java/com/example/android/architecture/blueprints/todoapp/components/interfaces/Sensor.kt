package com.example.android.architecture.blueprints.todoapp.components.interfaces

import android.content.Context
import androidx.compose.runtime.MutableState
import com.example.android.architecture.blueprints.todoapp.model.Acceleration

interface Sensor {

    fun initiateSensor(context: Context)

    fun stopMeasurement()

    fun getMutableAcceleration(): MutableState<Acceleration?>
}
