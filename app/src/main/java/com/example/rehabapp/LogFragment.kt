package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rehabapp.ValuesToLoad.Companion.logText


class LogFragment : Fragment() {

    lateinit var log:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.log_layout, container, false)
        log=view.findViewById(R.id.log)
        log.append(logText)

        return view
    }
}