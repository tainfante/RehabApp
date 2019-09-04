package com.example.rehabapp

import android.content.Context
import java.io.File

class SaveToFile(val context: Context) {

    private var file: File? = null
    private var directory: File? = null

    fun createFile(filename:String){

        val path = context.getExternalFilesDir(null)
        directory = File(path, "DATA")
        val success = directory!!.mkdirs()
        file = File(directory, filename)

    }
    fun saveToFile(){

        file?.appendText("eloelo")

    }
}