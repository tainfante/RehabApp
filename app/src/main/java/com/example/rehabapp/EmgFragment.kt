package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rehabapp.ValuesToLoad.Companion.emgEntries
import com.example.rehabapp.ValuesToLoad.Companion.fftEntries
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class EmgFragment : Fragment() {

    lateinit var emgChart: LineChart
    lateinit var fftChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.emg_layout, container, false)

        emgChart=view.findViewById(R.id.emgChart)

        val emgDataSet=LineDataSet(emgEntries, "Random")

        val emgLineData= LineData(emgDataSet)
        emgChart.data = emgLineData
        emgChart.invalidate()

        fftChart=view.findViewById(R.id.fftChart)

        val fftDataSet=LineDataSet(fftEntries, "Random")

        val fftLineData= LineData(fftDataSet)
        fftChart.data = fftLineData
        fftChart.invalidate()


        return view
    }
}