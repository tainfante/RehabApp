package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.widget.ArrayAdapter
import android.widget.Spinner

class ComFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View=inflater.inflate(R.layout.com_layout, container, false)


        val selectDeviceSpinner: Spinner = view.findViewById(R.id.spinner)
        val startButton:Button=view.findViewById(R.id.startButton)
        val stopButton:Button=view.findViewById(R.id.stopButton)

        val devicesList: ArrayList<BluetoothDevice> = (activity as MainActivity).pairedDeviceList()
        val devicesNamesList: ArrayList<String> = ArrayList()
        for(device:BluetoothDevice in devicesList){
            devicesNamesList.add(device.name)
        }
        val adapter=ArrayAdapter((activity as MainActivity).applicationContext,android.R.layout.simple_spinner_item, devicesNamesList)
        adapter.setNotifyOnChange(true)
        selectDeviceSpinner.adapter=adapter

        startButton.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).startChronometer()
        })

        stopButton.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).stopChronometer()
        })

        return view
    }
}