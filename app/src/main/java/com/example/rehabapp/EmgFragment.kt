package com.example.rehabapp


import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.Plot
import com.androidplot.xy.*
import java.text.DecimalFormat
import java.util.*


class EmgFragment(private val queueHandOuter: QueueHandOuter) : Fragment() {

    lateinit var emgChart: XYPlot
    lateinit var data: RealTimeXYDatasource
    lateinit var myThread: Thread

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.emg_layout, container, false)

        emgChart=view.findViewById(R.id.emgChart)

        val plotUpdater=MyPlotUpdater(emgChart)
        emgChart.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = DecimalFormat("0")
        emgChart.domainStepMode = StepMode.INCREMENT_BY_VAL
        emgChart.domainStepValue = 400.0
        emgChart.rangeStepMode = StepMode.INCREMENT_BY_VAL
        emgChart.rangeStepValue = 10000.0
        emgChart.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = DecimalFormat("0")

        emgChart.setRangeBoundaries(0,4095, BoundaryMode.FIXED)

        data= RealTimeXYDatasource(this.queueHandOuter, 0)

        val rawSeries = RealTimeSeries(data, 0, "Raw")
        val filteredSeries = RealTimeSeries(data, 1, "Filtered")

        val formatter1 = LineAndPointFormatter(Color.rgb(0, 200, 0), null, null, null)
        formatter1.linePaint.strokeJoin = Paint.Join.ROUND
        formatter1.linePaint.strokeWidth = 2f
        emgChart.addSeries(rawSeries, formatter1)

        val formatter2 = LineAndPointFormatter(Color.rgb(0, 0, 200), null, null, null)
        formatter2.linePaint.strokeJoin = Paint.Join.ROUND
        formatter2.linePaint.strokeWidth = 2f
        emgChart.addSeries(filteredSeries, formatter2)

        data.addObserver(plotUpdater)

        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser&&isResumed){
            onResume()
        } else if(!isVisibleToUser&&isResumed){
            onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if((!userVisibleHint)) return
        myThread = Thread(data)
        myThread.start()
    }

    override fun onPause() {
        data.stopThread()
        super.onPause()
    }

    inner class MyPlotUpdater(private var emgplot: Plot<*, *, *, *, *>) : Observer {

        override fun update(o: Observable?, arg: Any?) {
            emgplot.redraw()
        }

    }
}