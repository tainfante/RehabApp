package com.example.rehabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import android.R.attr.start
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast




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
            running = true
        }
    }
    fun stopChronometer() {
        if (running) {
            chronometer.stop()
            chronometer.base = SystemClock.elapsedRealtime()
            running = false
        }
    }

}
