package com.example.rehabapp

import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.rehabapp.ValuesToLoad.Companion.rawEmgEntries
import com.example.rehabapp.ValuesToLoad.Companion.fftEntries
import com.example.rehabapp.ValuesToLoad.Companion.filtredEmgEntries
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class EmgFragment : Fragment() {

    lateinit var emgChart: LineChart
    lateinit var fftChart: LineChart

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.emg_layout, container, false)

        var color1 = ContextCompat.getColor((activity as MainActivity).applicationContext, R.color.colorblue)
        var color2 = ContextCompat.getColor((activity as MainActivity).applicationContext, R.color.colorpink)

        emgChart=view.findViewById(R.id.emgChart)

        val emgDataSet=LineDataSet(rawEmgEntries, "Raw EMG")
        emgDataSet.lineWidth = 0.4f
        emgDataSet.setDrawCircles(false)
        emgDataSet.color = color1
        
        val filtredEmgDataSet=LineDataSet(filtredEmgEntries, "Filtred EMG")
        filtredEmgDataSet.lineWidth=0.4f
        filtredEmgDataSet.setDrawCircles(false)
        filtredEmgDataSet.color = color2

        var emgDataSets = ArrayList<ILineDataSet>()
        emgDataSets.add(emgDataSet)
        emgDataSets.add(filtredEmgDataSet)

        val emgLineData= LineData(emgDataSets)
        emgChart.data = emgLineData
        emgChart.axisLeft.axisMinimum = 0.0F
        emgChart.axisLeft.axisMaximum = 4095.0F
        emgChart.axisRight.isEnabled = false
        emgChart.invalidate()

        fftChart=view.findViewById(R.id.fftChart)

        val fftDataSet=LineDataSet(fftEntries, "Random")

        val fftLineData= LineData(fftDataSet)
        fftChart.data = fftLineData
        fftChart.invalidate()


        return view
    }
}