package pl.birski.falldetector.architecture.blueprints.todoapp.components.implementations

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import pl.birski.falldetector.architecture.blueprints.todoapp.R
import pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces.FallDetector
import pl.birski.falldetector.architecture.blueprints.todoapp.model.Acceleration
import pl.birski.falldetector.architecture.blueprints.todoapp.other.Constants
import timber.log.Timber
import javax.inject.Inject

class SensorImpl @Inject constructor(
    private val fallDetector: FallDetector
) : pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces.Sensor, SensorEventListener {

    private var manager: SensorManager? = null

    private val acceleration: MutableState<Acceleration?> = mutableStateOf(null)

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                val rawAcceleration = createAcceleration(event = event)
                acceleration.value = rawAcceleration
                fallDetector.detectFall(rawAcceleration)
                Timber.d("Raw acceleration is equal to: $rawAcceleration")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        when (sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> Timber.d("Accuracy  is equal to $accuracy")
        }
    }

    override fun initiateSensor(context: Context) {
        manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensor?.let {
            manager?.registerListener(this, sensor, Constants.INTERVAL_MILISEC * 1000)
        } ?: Toast.makeText(
            context,
            context.getText(R.string.accelerometer_not_supported_toast_text),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun stopMeasurement() {
        manager?.unregisterListener(this)
    }

    override fun getMutableAcceleration() = acceleration

    private fun createAcceleration(event: SensorEvent) = Acceleration(
        event.values[0].div(SensorManager.STANDARD_GRAVITY).toDouble(),
        event.values[1].div(SensorManager.STANDARD_GRAVITY).toDouble(),
        event.values[2].div(SensorManager.STANDARD_GRAVITY).toDouble(),
        event.timestamp / 1000000
    )
}
