package com.example.rehabapp

import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.rehabapp.ValuesToLoad.Companion.rawEmgEntries
import com.example.rehabapp.ValuesToLoad.Companion.fftEntries
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


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

        emgChart=view.findViewById(R.id.emgChart)
        val context = (activity as MainActivity).applicationContext
        var db = DatabaseHelper(context)
        val data: Cursor = db.getRawEmgValues()
        while(data.moveToNext()){
            rawEmgEntries.add(Entry(1.0F, data.getInt(1).toFloat()))
        }
        val emgDataSet=LineDataSet(rawEmgEntries, "Raw EMG")

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