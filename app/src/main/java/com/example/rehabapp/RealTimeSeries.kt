package com.example.rehabapp

import com.androidplot.xy.XYSeries

 class RealTimeSeries(
    private val datasource: RealTimeXYDatasource,
    private val seriesIndex: Int,
    private val title: String
) : XYSeries {

    override fun getTitle(): String {
        return title
    }

    override fun size(): Int {
        return datasource.getItemCount(seriesIndex)
    }

    override fun getX(index: Int): Number {
        return datasource.getX(seriesIndex, index)
    }

    override fun getY(index: Int): Number {
        return datasource.getY(seriesIndex, index)
    }
}