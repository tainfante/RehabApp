package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.graphics.Bitmap
import java.text.SimpleDateFormat
import java.util.*


class ComFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View=inflater.inflate(R.layout.com_layout, container, false)

        val startButton:Button=view.findViewById(R.id.startButton)
        val stopButton:Button=view.findViewById(R.id.stopButton)

        startButton.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).startChronometer()
        })

        stopButton.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).stopChronometer()
        })

        return view
    }
}