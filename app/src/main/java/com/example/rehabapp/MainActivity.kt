package com.example.rehabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import com.example.rehabapp.ValuesToLoad.Companion.logText
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.collections.ArrayList


@ExperimentalUnsignedTypes
class MainActivity : AppCompatActivity(), ComFragment.ComFragmentInterface, ConnectDevice.IsReadyForTransmition, QueueHandOuter {


    lateinit var sectionsPageAdapter: SectionsPageAdapter
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    var running = false
    private var macaddress : String = ""
    var isDevicePicked = false
    private lateinit var chronometer: Chronometer
    private var connectedDevice = ConnectDevice(this, this)
    private val queueHandler = QueueHandler()
    private val saveToFile = SaveToFile(this)


    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionsPageAdapter = SectionsPageAdapter(super.getSupportFragmentManager())

        viewPager = findViewById(R.id.container)
        setupViewPager(viewPager)

        tabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        chronometer = findViewById(R.id.chronometer)
        chronometer.format = "Time: %s"
        chronometer.base = SystemClock.elapsedRealtime()

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "This device doesn't support bluetooth", Toast.LENGTH_LONG).show()
        }
        if (!bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

    }

    private fun addLog(log: String) {
        val calendar = Calendar.getInstance()
        val h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        val result = "$h:$m"
        logText = ("$logText$result $log")
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(super.getSupportFragmentManager())
        adapter.addFragment(ComFragment(), "COM")
        adapter.addFragment(HistFragment(), "HIST")
        adapter.addFragment(EmgFragment(), "EMG")
        adapter.addFragment(AccFragment(), "ACC")
        adapter.addFragment(LogFragment(), "LOG")
        viewPager.adapter = adapter
    }

    private fun startChronometer() {
        if (!running) {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            addLog("Started measurement\n")
            running = true
        }
    }

    private fun stopChronometer() {
        if (running) {
            chronometer.stop()
            addLog("Stopped measurement\n")
            running = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Bluetooth disabled", Toast.LENGTH_LONG).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Bluetooth enabling canceled", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDevicePicked(macaddress: String, deviceName: String) {
        this.macaddress=macaddress
        Toast.makeText(this, "Selected: $deviceName $macaddress", Toast.LENGTH_LONG).show()
        isDevicePicked=true
    }

    override fun onStartButtonClicked() {
        connectedDevice.macAddress=macaddress
        connectedDevice.execute()
        saveToFile.createFile("eloelo.txt")
        saveToFile.saveToFile()

    }

    override fun onStopButtonClicked() {
        connectedDevice.disconnect()
        stopChronometer()
    }

    override fun getPairedDeviceList(): ArrayList<BluetoothDevice> {
        pairedDevices = bluetoothAdapter!!.bondedDevices
        val list: ArrayList<BluetoothDevice> = ArrayList()
        if (pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in pairedDevices) {
                list.add(device)
            }
        }
        return list
    }
    override fun isDeviceReady(isReady: Boolean) {
        if(isReady){
            val result = connectedDevice.getDeviceConnectResult()
            addLog(result)
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            startChronometer()
            connectedDevice.beginListenForData()
        }else{
            val result = connectedDevice.getDeviceConnectResult()
            addLog(result)
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        }
    }

    override fun giveMeTheAccQueue(): LinkedBlockingQueue<AccFrame> {
        return queueHandler.getAccQueue()
    }

    override fun giveMeTheEmgQueue(): LinkedBlockingQueue<EmgFrame> {
        return queueHandler.getEmgQueue()
    }
}
