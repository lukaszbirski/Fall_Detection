package pl.birski.falldetector.components.interfaces

import android.content.Context
import androidx.compose.runtime.MutableState
import pl.birski.falldetector.model.Acceleration

interface Sensor {

    fun initiateSensor(context: Context)

    fun stopMeasurement()

    fun getMutableAcceleration(): MutableState<Acceleration?>
}
