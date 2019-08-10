package com.example.rehabapp

import com.github.mikephil.charting.data.Entry

class ValuesToLoad {

    companion object{
        var logText: String = ""
        val rawEmgEntries = ArrayList<Entry>()
        val filtredEmgEntries = ArrayList<Entry>()
        val fftEntries = ArrayList<Entry>()
        val accEntries = ArrayList<Entry>()
        val gyrEntries = ArrayList<Entry>()
    }

}