package pl.birski.falldetector.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.birski.falldetector.R
import pl.birski.falldetector.components.interfaces.LocationTracker
import pl.birski.falldetector.components.interfaces.Sensor
import pl.birski.falldetector.model.Acceleration
import pl.birski.falldetector.service.TrackingService
import pl.birski.falldetector.service.enum.DataSet
import pl.birski.falldetector.service.enum.ServiceActions
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val sensor: Sensor
) : ViewModel() {

    private val _acceleration = MutableLiveData<Acceleration>()
    val acceleration: LiveData<Acceleration> get() = _acceleration

    private var job: Job? = null

    private val GRAPH_UPDATE_SLEEP_TIME = 50L

    fun startService(context: Context) =
        sendCommandToService(context, ServiceActions.START_OR_RESUME).also {
            sensor.initiateSensor(context)
            runGraphUpdate()
        }

    fun stopService(context: Context) = sendCommandToService(context, ServiceActions.STOP)
        .also {
            sensor.stopMeasurement()
            stopGraphUpdates()
        }

    fun handleLineData(data: LineData, acceleration: Acceleration, context: Context): LineData {
        val xData = data.getDataSetByIndex(0) ?: createSet(DataSet.X_AXIS, context)
            .also { data.addDataSet(it) }

        val yData = data.getDataSetByIndex(1) ?: createSet(DataSet.Y_AXIS, context)
            .also { data.addDataSet(it) }

        val zData = data.getDataSetByIndex(2) ?: createSet(DataSet.Z_AXIS, context)
            .also { data.addDataSet(it) }

        data.addEntry(createEntry(acceleration, xData, DataSet.X_AXIS), 0)
        data.addEntry(createEntry(acceleration, yData, DataSet.Y_AXIS), 1)
        data.addEntry(createEntry(acceleration, zData, DataSet.Z_AXIS), 2)
        return data
    }

    fun enableLocationService(context: Context) {
        if (!locationTracker.locationEnabled()) {
            locationTracker.showSettingsAlert(context)
        }
    }

    private fun createSet(axis: DataSet, context: Context) =
        LineDataSet(null, selectDescription(axis, context))
            .also {
                it.axisDependency = YAxis.AxisDependency.LEFT
                it.lineWidth = 1f
                it.isHighlightEnabled = false
                it.setDrawValues(false)
                it.setDrawCircles(false)
                it.mode = LineDataSet.Mode.CUBIC_BEZIER
                it.cubicIntensity = 0.2f
                it.color = selectLineColor(axis)
            }

    private fun createEntry(
        acceleration: Acceleration,
        measurement: ILineDataSet,
        dataSet: DataSet
    ) = Entry(
        measurement.entryCount.toFloat(),
        selectValue(acceleration, dataSet).toFloat()
    )

    private suspend fun updateGraph() {
        stopGraphUpdates()
        job = viewModelScope.launch {
            while (true) {
                measureAcceleration()
                delay(GRAPH_UPDATE_SLEEP_TIME)
            }
        }
    }

    private fun runGraphUpdate() {
        viewModelScope.launch {
            updateGraph()
        }
    }

    private fun stopGraphUpdates() {
        job?.cancel()
        job = null
    }

    private fun sendCommandToService(context: Context, action: ServiceActions) =
        Intent(context, TrackingService::class.java).also {
            it.action = action.name
            context.startService(it)
        }

    private fun measureAcceleration() {
        sensor.getMutableAcceleration().value?.let {
            Timber.d("Measured acceleration value is: $it")
            _acceleration.postValue(it)
        }
    }

    private fun selectDescription(axis: DataSet, context: Context) = when (axis) {
        DataSet.X_AXIS -> R.string.graph_fragment_x_axis_acc_text
        DataSet.Y_AXIS -> R.string.graph_fragment_y_axis_acc_text
        DataSet.Z_AXIS -> R.string.graph_fragment_z_axis_acc_text
    }.let { context.getString(it) }

    private fun selectLineColor(axis: DataSet) = when (axis) {
        DataSet.X_AXIS -> Color.BLUE
        DataSet.Y_AXIS -> Color.GREEN
        DataSet.Z_AXIS -> Color.RED
    }

    private fun selectValue(acceleration: Acceleration, dataSet: DataSet) = when (dataSet) {
        DataSet.X_AXIS -> acceleration.x
        DataSet.Y_AXIS -> acceleration.y
        DataSet.Z_AXIS -> acceleration.z
    }
}
