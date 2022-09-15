package pl.birski.falldetector.presentation.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.falldetector.databinding.FragmentGraphBinding
import pl.birski.falldetector.model.Acceleration
import pl.birski.falldetector.other.Constants
import pl.birski.falldetector.presentation.viewmodel.GraphViewModel
import pl.birski.falldetector.service.enum.DataSet

@AndroidEntryPoint
class GraphFragment : Fragment() {

    private lateinit var binding: FragmentGraphBinding

    lateinit var viewModel: GraphViewModel

    private val VISIBLE_X_RANGE_MAX = 150F
    private val MAX_Y_AXIS_VALUE = 1.5F
    private val MIN_Y_AXIS_VALUE = -1.5F
    private val THREAD_SLEEP_TIME = 10L

    private var thread: Thread? = null
    private var plotData = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[GraphViewModel::class.java]

        binding = FragmentGraphBinding.inflate(inflater, container, false)

        binding.startBtn.setOnClickListener {
            viewModel.startService(requireContext())
            sendBroadcast(false)
        }

        binding.stopBtn.setOnClickListener {
            viewModel.stopService(requireContext())
            sendBroadcast(true)
        }

        viewModel.apply {
            viewModel.acceleration.observe(viewLifecycleOwner) {
                if (plotData) {
                    addEntry(it)
                    plotData = false
                }
            }

            viewModel.enableLocationService(requireContext())
        }

        setChart(binding.chart)

        feedMultiple()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        thread?.interrupt()
    }

    override fun onDestroy() {
        thread?.interrupt()
        super.onDestroy()
    }

    private fun addEntry(acceleration: Acceleration) {
        val data = binding.chart.data

        data?.let {
            val xMeasurement =
                data.getDataSetByIndex(0) ?: viewModel.createSet(DataSet.X_AXIS, requireContext())
                    .also { data.addDataSet(it) }

            val yMeasurement =
                data.getDataSetByIndex(1) ?: viewModel.createSet(DataSet.Y_AXIS, requireContext())
                    .also { data.addDataSet(it) }

            val zMeasurement =
                data.getDataSetByIndex(2) ?: viewModel.createSet(DataSet.Z_AXIS, requireContext())
                    .also { data.addDataSet(it) }

            data.addEntry(viewModel.createEntry(acceleration, xMeasurement, DataSet.X_AXIS), 0)
            data.addEntry(viewModel.createEntry(acceleration, yMeasurement, DataSet.Y_AXIS), 1)
            data.addEntry(viewModel.createEntry(acceleration, zMeasurement, DataSet.Z_AXIS), 2)

            data.notifyDataChanged()

            binding.chart.notifyDataSetChanged()
            binding.chart.setVisibleXRangeMaximum(VISIBLE_X_RANGE_MAX)
            binding.chart.moveViewToX(data.entryCount.toFloat())
        }
    }

    private fun feedMultiple() {
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

    private fun setChart(chart: LineChart) {
        chart.apply {
            // disable description text
            description.isEnabled = false

            // enable touch gestures
            setTouchEnabled(false)

            // enable scaling and dragging
            isDragEnabled = false
            setScaleEnabled(true)
            setDrawGridBackground(true)

            // if disabled, scaling can be done on x- and y-axis separately
            setPinchZoom(true)

            // set an alternative background color
            setBackgroundColor(Color.WHITE)
            val lineData = LineData()
            lineData.setValueTextColor(Color.WHITE)

            // add empty data
            data = lineData

            // get the legend (only possible after setting data)
            val l = legend

            // modify the legend ...
            l.form = Legend.LegendForm.LINE
            l.textColor = Color.BLACK
            val xl = xAxis
            xl.textColor = Color.WHITE
            xl.setDrawGridLines(true)
            xl.setAvoidFirstLastClipping(true)
            xl.isEnabled = true
            val leftAxis = axisLeft
            leftAxis.textColor = Color.BLACK
            leftAxis.setDrawGridLines(true)
            leftAxis.axisMaximum = MAX_Y_AXIS_VALUE
            leftAxis.axisMinimum = MIN_Y_AXIS_VALUE
            leftAxis.setDrawGridLines(true)
            val rightAxis = axisRight
            rightAxis.isEnabled = false
            setDrawBorders(true)
        }
    }

    private fun sendBroadcast(value: Boolean) =
        Intent(Constants.CUSTOM_FALL_DETECTED_INTENT_INTERACTOR).also {
            it.putExtra("boolean", value)
            context?.sendBroadcast(it)
        }
}
