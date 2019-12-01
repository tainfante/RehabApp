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
import com.androidplot.xy.XYPlot



class EmgFragment : Fragment() {

    lateinit var emgChart: XYPlot

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



        return view
    }
}