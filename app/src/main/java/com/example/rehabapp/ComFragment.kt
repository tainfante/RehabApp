package com.example.rehabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.bluetooth.BluetoothDevice
import android.widget.*

class ComFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var devicesList:ArrayList<BluetoothDevice>
    var justCreatedView = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View=inflater.inflate(R.layout.com_layout, container, false)

        val selectDeviceSpinner: Spinner = view.findViewById(R.id.spinner)
        val startButton:Button=view.findViewById(R.id.startButton)
        val stopButton:Button=view.findViewById(R.id.stopButton)

        //variable for avoiding calling onItemSelected after creating view
        justCreatedView=true

        //populating the list of bluetooth devices
        devicesList = (activity as ComFragmentInterface).getPairedDeviceList()
        val devicesNamesList: ArrayList<String> = ArrayList()
        devicesNamesList.add(0, "BT Device")
        for(device:BluetoothDevice in devicesList){
            devicesNamesList.add(device.name)
        }
        val adapter=ArrayAdapter((activity as MainActivity).applicationContext,R.layout.spinner_item, devicesNamesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectDeviceSpinner.adapter=adapter

        selectDeviceSpinner.onItemSelectedListener = this


        startButton.setOnClickListener(View.OnClickListener {
            (activity as ComFragmentInterface).onStartButtonClicked()
        })

        stopButton.setOnClickListener(View.OnClickListener {
            (activity as ComFragmentInterface).onStopButtonClicked()
        })

        return view
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent!!.getItemAtPosition(position) == "BT Device" || justCreatedView){
            justCreatedView=false
        } else{
            for(device:BluetoothDevice in devicesList){
                if (device.name== parent.getItemAtPosition(position)){
                    (activity as ComFragmentInterface).onDevicePicked(device.address, device.name)
                }
            }
        }

    }
    interface ComFragmentInterface {
        fun onDevicePicked(macaddress: String, deviceName: String)
        fun onStartButtonClicked()
        fun onStopButtonClicked()
        fun getPairedDeviceList():ArrayList<BluetoothDevice>
    }

}