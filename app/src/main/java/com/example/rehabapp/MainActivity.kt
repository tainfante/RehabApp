package com.example.rehabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import android.R.attr.start
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.rehabapp.ValuesToLoad.Companion.logText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var sectionsPageAdapter: SectionsPageAdapter
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    var running = false
    lateinit var chronometer: Chronometer

    private var bluetoothAdapter: BluetoothAdapter?=null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object{
        val EXTRA_ADDRESS:String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionsPageAdapter = SectionsPageAdapter(super.getSupportFragmentManager())

        viewPager = findViewById(R.id.container) as ViewPager
        setupViewPager(viewPager)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager);

        chronometer=findViewById(R.id.chronometer)
        chronometer.format = "Time: %s"
        chronometer.base = SystemClock.elapsedRealtime()

        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter==null){
            Toast.makeText(this, "This device doesn't support bluetooth", Toast.LENGTH_LONG).show()
        }
        if(!bluetoothAdapter!!.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        }

    @RequiresApi(Build.VERSION_CODES.P)
    fun insertEmgData(emgRaw: Int, emgFiltred: Int){
        val context = this
        var db = DatabaseHelper(context)
        db.insertData(emgRaw, emgFiltred)
    }

    fun addLog(log: String) {
        val calendar = Calendar.getInstance()
        val h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        val result = "$h:$m"
        logText=("$logText$result $log")
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(super.getSupportFragmentManager())
        adapter.addFragment(ComFragment(), "COM")
        adapter.addFragment(EmgFragment(), "EMG")
        adapter.addFragment(AccFragment(), "ACC")
        adapter.addFragment(HistFragment(), "HIST")
        adapter.addFragment(LogFragment(), "LOG")
        viewPager.adapter = adapter
    }

    fun startChronometer() {
        if (!running) {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            addLog("Started measurement\n")
            running = true
        }
    }
    fun stopChronometer() {
        if (running) {
            chronometer.stop()
            addLog("Stopped measurement\n")
            running = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_ENABLE_BLUETOOTH){
            if(resultCode==Activity.RESULT_OK){
                if(bluetoothAdapter!!.isEnabled){
                    Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Bluetooth disabled", Toast.LENGTH_LONG).show()
                }
            } else if(resultCode==Activity.RESULT_CANCELED){
                Toast.makeText(this, "Bluetooth enabling canceled", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun pairedDeviceList():ArrayList<BluetoothDevice>{
        pairedDevices=bluetoothAdapter!!.bondedDevices
        val list: ArrayList<BluetoothDevice> = ArrayList()
        if(pairedDevices.isNotEmpty()){
            for(device:BluetoothDevice in pairedDevices){
                list.add(device)
                Log.i("device", ""+device)
            }
        }
        return list
    }

}
