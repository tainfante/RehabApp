package com.example.rehabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import android.R.attr.start
import android.os.Build
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.rehabapp.ValuesToLoad.Companion.logText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var sectionsPageAdapter: SectionsPageAdapter
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    var running = false
    lateinit var chronometer: Chronometer

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

}
