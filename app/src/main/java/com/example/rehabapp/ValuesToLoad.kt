package com.example.rehabapp

import com.github.mikephil.charting.data.Entry

class ValuesToLoad {

    companion object{
        var logText: String = ""
        val rawEmgEntries = ArrayList<Entry>()
        val filtredEmgEntries = ArrayList<Entry>()
        val fftEntries = ArrayList<Entry>()
        val accXEntries = ArrayList<Entry>()
        val accYEntries = ArrayList<Entry>()
        val accZEntries = ArrayList<Entry>()
        val gyrXEntries = ArrayList<Entry>()
        val gyrYEntries = ArrayList<Entry>()
        val gyrZEntries = ArrayList<Entry>()
    }

}