package pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces

import android.content.Context
import androidx.compose.runtime.MutableState
import pl.birski.falldetector.architecture.blueprints.todoapp.model.Acceleration

interface Sensor {

    fun initiateSensor(context: Context)

    fun stopMeasurement()

    fun getMutableAcceleration(): MutableState<Acceleration?>
}
