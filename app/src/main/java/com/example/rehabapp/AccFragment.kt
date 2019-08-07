package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rehabapp.ValuesToLoad.Companion.accEntries
import com.example.rehabapp.ValuesToLoad.Companion.gyrEntries
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class AccFragment : Fragment() {

    lateinit var accChart: LineChart
    lateinit var gyrChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View =inflater.inflate(R.layout.acc_layout, container, false)

        accChart=view.findViewById(R.id.accChart)

        val accDataSet= LineDataSet(accEntries, "Random")

        val accLineData= LineData(accDataSet)
        accChart.data = accLineData
        accChart.invalidate()

        gyrChart=view.findViewById(R.id.gyrChart)

        val gyrDataSet= LineDataSet(gyrEntries, "Random")

        val gyrLineData= LineData(gyrDataSet)
        gyrChart.data = gyrLineData
        gyrChart.invalidate()

        return view
    }

}