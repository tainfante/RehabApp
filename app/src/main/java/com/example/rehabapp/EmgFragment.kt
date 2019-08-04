package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class EmgFragment : Fragment() {

    lateinit var chart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.emg_layout, container, false)

        chart=view.findViewById(R.id.emgChart)
        val entries = ArrayList<Entry>()
        entries.add(Entry(1.0F, 2.0F))
        entries.add(Entry(2.0F, 8.0F))
        entries.add(Entry(3.0F, 13.0F))
        entries.add(Entry(4.0F, 15.0F))
        entries.add(Entry(5.0F, 7.0F))
        entries.add(Entry(6.0F, 3.0F))

        val dataSet=LineDataSet(entries, "Random")

        val lineData= LineData(dataSet)
        chart.data = lineData
        chart.invalidate()


        return view
    }
}