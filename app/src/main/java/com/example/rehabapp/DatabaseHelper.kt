package com.example.rehabapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

val EMG_DATABASE_NAME = "emgDatabase.db"
val EMG_TABLE_NAME = "emg_data"
val EMGCOL1 = "ID"
val EMGCOL2 = "VALUE_RAW"
val EMGCOL3 = "VALUE_FILTRED"


@RequiresApi(Build.VERSION_CODES.P)
class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, EMG_DATABASE_NAME, null, 1) {



    override fun onCreate(p0: SQLiteDatabase?) {

        val createTable="CREATE TABLE " + EMG_TABLE_NAME + " (" + EMGCOL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMGCOL2 + " INTEGER," + EMGCOL3 + " INTEGER)"
        p0?.execSQL(createTable)
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertData(emgRaw: Int, emgFiltred: Int){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(EMGCOL2, emgRaw)
        cv.put(EMGCOL3, emgFiltred)
        var result = db.insert(EMG_TABLE_NAME, null, cv)
        if(result==-1.toLong()){
            Toast.makeText(context, "Failed to insert", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRawEmgValues():Cursor{
        val db = this.writableDatabase
        val data: Cursor = db.rawQuery("SELECT * FROM " + EMG_TABLE_NAME, null)
        return data
    }



}