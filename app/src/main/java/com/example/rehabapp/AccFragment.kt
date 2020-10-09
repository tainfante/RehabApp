package com.example.rehabapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.Plot
import java.util.*
import android.graphics.Paint.Join
import com.androidplot.xy.*
import java.text.DecimalFormat

class AccFragment(private val queueHandOuter: QueueHandOuter) : Fragment() {

    lateinit var accChart: XYPlot
    lateinit var gyrChart: XYPlot
    lateinit var data: RealTimeXYDatasource
    lateinit var myThread: Thread


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =inflater.inflate(R.layout.acc_layout, container, false)

        println("Accfragment oncreateview")

        accChart=view.findViewById(R.id.accChart)
        gyrChart=view.findViewById(R.id.gyrChart)

        val plotUpdater=MyPlotUpdater(accChart, gyrChart)
        accChart.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = DecimalFormat("0")
        accChart.domainStepMode = StepMode.INCREMENT_BY_VAL
        accChart.domainStepValue = 400.0
        accChart.rangeStepMode = StepMode.INCREMENT_BY_VAL
        accChart.rangeStepValue = 10000.0
        accChart.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = DecimalFormat("0")

        gyrChart.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = DecimalFormat("0")
        gyrChart.domainStepMode = StepMode.INCREMENT_BY_VAL
        gyrChart.domainStepValue = 400.0
        gyrChart.rangeStepMode = StepMode.INCREMENT_BY_VAL
        gyrChart.rangeStepValue = 10000.0
        gyrChart.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = DecimalFormat("0")

        accChart.setRangeBoundaries(-32768,32768, BoundaryMode.FIXED)
        gyrChart.setRangeBoundaries(-32768,32768, BoundaryMode.FIXED)

        data = RealTimeXYDatasource(this.queueHandOuter,1)

        val accXseries = RealTimeSeries(data, 0, "ACC X")
        val accYseries = RealTimeSeries(data, 1, "ACC Y")
        val accZseries = RealTimeSeries(data, 2, "ACC Z")

        val gyrXseries = RealTimeSeries(data, 3, "GYR X")
        val gyrYseries = RealTimeSeries(data, 4, "GYR Y")
        val gyrZseries = RealTimeSeries(data, 5, "GYR Z")

        val formatter1 = LineAndPointFormatter(Color.rgb(0, 200, 0), null, null, null)
        formatter1.linePaint.strokeJoin = Join.ROUND
        formatter1.linePaint.strokeWidth = 2f
        accChart.addSeries(accXseries,formatter1)
        gyrChart.addSeries(gyrXseries, formatter1)

        val formatter2 = LineAndPointFormatter(Color.rgb(0, 0, 200), null, null, null)
        formatter2.linePaint.strokeJoin = Join.ROUND
        formatter2.linePaint.strokeWidth = 2f
        accChart.addSeries(accYseries,formatter2)
        gyrChart.addSeries(gyrYseries, formatter2)

        val formatter3 = LineAndPointFormatter(Color.rgb(200, 0, 0), null, null, null)
        formatter3.linePaint.strokeJoin = Join.ROUND
        formatter3.linePaint.strokeWidth = 2f
        accChart.addSeries(accZseries,formatter3)
        gyrChart.addSeries(gyrZseries, formatter3)

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

    inner class MyPlotUpdater(private var accplot: Plot<*, *, *, *, *>, private var gyrplot: Plot<*, *, *, *, *> ) : Observer {

        override fun update(o: Observable?, arg: Any?) {
            accplot.redraw()
            gyrplot.redraw()
        }

    }
}