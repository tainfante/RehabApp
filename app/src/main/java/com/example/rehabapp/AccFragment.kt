package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.example.rehabapp.ValuesToLoad.Companion.accXEntries
import com.example.rehabapp.ValuesToLoad.Companion.accYEntries
import com.example.rehabapp.ValuesToLoad.Companion.accZEntries

import com.example.rehabapp.ValuesToLoad.Companion.gyrXEntries
import com.example.rehabapp.ValuesToLoad.Companion.gyrYEntries
import com.example.rehabapp.ValuesToLoad.Companion.gyrZEntries
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class AccFragment : Fragment() {

    lateinit var accChart: LineChart
    lateinit var gyrChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View =inflater.inflate(R.layout.acc_layout, container, false)

        var color1 = ContextCompat.getColor((activity as MainActivity).applicationContext, R.color.colorblue)
        var color2 = ContextCompat.getColor((activity as MainActivity).applicationContext, R.color.colorpink)
        var color3 = ContextCompat.getColor((activity as MainActivity).applicationContext, R.color.colorgreen)

        accChart=view.findViewById(R.id.accChart)

        val accXDataSet=LineDataSet(accXEntries, "X")
        accXDataSet.lineWidth = 0.4f
        accXDataSet.setDrawCircles(false)
        accXDataSet.color = color1

        val accYDataSet=LineDataSet(accYEntries, "Y")
        accYDataSet.lineWidth = 0.4f
        accYDataSet.setDrawCircles(false)
        accYDataSet.color = color2

        val accZDataSet=LineDataSet(accZEntries, "Z")
        accZDataSet.lineWidth = 0.4f
        accZDataSet.setDrawCircles(false)
        accZDataSet.color = color3

        var accDataSets = ArrayList<ILineDataSet>()
        accDataSets.add(accXDataSet)
        accDataSets.add(accYDataSet)
        accDataSets.add(accZDataSet)

        val accLineData= LineData(accDataSets)
        accChart.data = accLineData
        accChart.axisLeft.axisMinimum = 0.0F
        accChart.axisLeft.axisMaximum = 65535.0F
        accChart.axisRight.isEnabled = false
        accChart.invalidate()

        gyrChart=view.findViewById(R.id.gyrChart)

        val gyrXDataSet=LineDataSet(gyrXEntries, "X")
        gyrXDataSet.lineWidth = 0.4f
        gyrXDataSet.setDrawCircles(false)
        gyrXDataSet.color = color1

        val gyrYDataSet=LineDataSet(gyrYEntries, "Y")
        gyrYDataSet.lineWidth = 0.4f
        gyrYDataSet.setDrawCircles(false)
        gyrYDataSet.color = color2

        val gyrZDataSet=LineDataSet(gyrZEntries, "Z")
        gyrZDataSet.lineWidth = 0.4f
        gyrZDataSet.setDrawCircles(false)
        gyrZDataSet.color=color3

        var gyrDataSets = ArrayList<ILineDataSet>()
        gyrDataSets.add(gyrXDataSet)
        gyrDataSets.add(gyrYDataSet)
        gyrDataSets.add(gyrZDataSet)

        val gyrLineData= LineData(gyrDataSets)
        gyrChart.data = gyrLineData
        gyrChart.axisLeft.axisMinimum = 0.0F
        gyrChart.axisLeft.axisMaximum = 65535.0F
        gyrChart.axisRight.isEnabled = false
        gyrChart.invalidate()

        return view
    }

}