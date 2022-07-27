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
import pl.birski.falldetector.architecture.blueprints.todoapp.R
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

    private val _lineData = MutableLiveData<LineData?>()
    val lineData: LiveData<LineData?> get() = _lineData

    private var plotData = true
    private var job: Job? = null
    private var thread: Thread? = null

    private val GRAPH_UPDATE_SLEEP_TIME = 50L
    private val THREAD_SLEEP_TIME = 10L

    private suspend fun updateGraph(lineData: LineData?, context: Context) {
        stopGraphUpdates()
        job = viewModelScope.launch {
            while (true) {
                measureAcceleration(lineData = lineData, context = context)
                delay(GRAPH_UPDATE_SLEEP_TIME)
            }
        }
    }

    private fun runGraphUpdate(lineData: LineData?, context: Context) {
        viewModelScope.launch {
            updateGraph(lineData = lineData, context = context)
        }
    }

    private fun stopGraphUpdates() {
        job?.cancel()
        job = null
    }

    fun startService(lineData: LineData?, context: Context) =
        sendCommandToService(context, ServiceActions.START_OR_RESUME)
            .also {
                sensor.initiateSensor(context)
                runGraphUpdate(lineData = lineData, context = context)
            }

    fun stopService(context: Context) = sendCommandToService(context, ServiceActions.STOP)
        .also {
            sensor.stopMeasurement()
            stopGraphUpdates()
        }

    private fun sendCommandToService(context: Context, action: ServiceActions) =
        Intent(context, TrackingService::class.java).also {
            it.action = action.name
            context.startService(it)
        }

    private fun measureAcceleration(lineData: LineData?, context: Context) {
        sensor.getMutableAcceleration().value?.let {
            Timber.d("Measured acceleration value is: $it")
            if (plotData) {
                addEntry(
                    acceleration = it,
                    lineData = lineData,
                    context = context
                )
            }
            plotData = false
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

    private fun createEntry(
        acceleration: Acceleration,
        measurement: ILineDataSet,
        dataSet: DataSet
    ) = Entry(
        measurement.entryCount.toFloat(),
        selectValue(acceleration, dataSet).toFloat()
    )

    private fun selectValue(acceleration: Acceleration, dataSet: DataSet) = when (dataSet) {
        DataSet.X_AXIS -> acceleration.x
        DataSet.Y_AXIS -> acceleration.y
        DataSet.Z_AXIS -> acceleration.z
    }

    private fun addEntry(acceleration: Acceleration, lineData: LineData?, context: Context) {
        val data = lineData

        data?.let {
            val xMeasurement = data.getDataSetByIndex(0) ?: createSet(DataSet.X_AXIS, context)
                .also { data.addDataSet(it) }

            val yMeasurement = data.getDataSetByIndex(1) ?: createSet(DataSet.Y_AXIS, context)
                .also { data.addDataSet(it) }

            val zMeasurement = data.getDataSetByIndex(2) ?: createSet(DataSet.Z_AXIS, context)
                .also { data.addDataSet(it) }

            data.addEntry(createEntry(acceleration, xMeasurement, DataSet.X_AXIS), 0)
            data.addEntry(createEntry(acceleration, yMeasurement, DataSet.Y_AXIS), 1)
            data.addEntry(createEntry(acceleration, zMeasurement, DataSet.Z_AXIS), 2)

            data.notifyDataChanged()
            _lineData.postValue(data)
        }
    }

    fun feedMultiple() {
        thread?.interrupt()
        thread = Thread {
            while (true) {
                plotData = true
                try {
                    Thread.sleep(THREAD_SLEEP_TIME)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread?.start()
    }

    fun enableLocationService(context: Context) {
        if (!locationTracker.locationEnabled()) {
            locationTracker.showSettingsAlert(context)
        }
    }
}
