package com.example.rehabapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@ExperimentalUnsignedTypes
class ConnectDevice(isReady: IsReadyForTransmition, queueHandOuter: QueueHandOuter) : AsyncTask<Void, Void, String>() {

    private var connectSuccess: Boolean = true
    var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    var myBluetoothSocket: BluetoothSocket? = null
    lateinit var bluetoothAdapter: BluetoothAdapter
    var isConnected: Boolean = false
    lateinit var macAddress: String
    private var connectResult: String = " "
    var btInputStream: InputStream? = null
    var btOutputStream: OutputStream? = null
    private var stopThread = false
    private var isReady: IsReadyForTransmition? = null
    private var queueHandOuter: QueueHandOuter? = null

    init {
        this.isReady = isReady
        this.queueHandOuter = queueHandOuter
    }

    override fun doInBackground(vararg p0: Void?): String? {
        try {
            if (myBluetoothSocket == null || !isConnected) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(macAddress)
                myBluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                myBluetoothSocket!!.connect()
            }

        } catch (e: IOException) {
            e.printStackTrace()
            connectSuccess = false
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (!connectSuccess) {
            connectResult = "Error in connection\n"
            isReady?.isDeviceReady(false)

        } else {
            isConnected = true
            connectResult = "Successfully connected\n"
            isReady?.isDeviceReady(true)
        }
    }

    fun getDeviceConnectResult(): String {
        return connectResult
    }

    fun beginListenForData() {

        btInputStream = myBluetoothSocket?.inputStream
        btOutputStream = myBluetoothSocket?.outputStream
        val readAccBuffer = UByteArray(24)
        val readEmgBuffer = UByteArray(8)
        var readBufferPosition = 0
        var emg = 0
        var acc = 0
        var start = 0
        val readDataThread = Thread(Runnable {
            while (!stopThread) {
                try {
                    val bytesAvailable = btInputStream?.available()
                    if (bytesAvailable != null && bytesAvailable > 0) {
                        val packetBytes = ByteArray(bytesAvailable)
                        btInputStream!!.read(packetBytes)
                        for (i in 0 until bytesAvailable) {
                            val b = packetBytes[i]
                            val byteRead = b.toUByte()
                            if (byteRead == 12.toUByte() && start == 0) {
                                start = 1
                                readBufferPosition = 0
                            } else if (start == 1 && acc == 0 && emg == 0) {
                                if (byteRead == 13.toUByte()) {
                                    emg = 1
                                    acc = 0
                                } else if (byteRead == 15.toUByte()) {
                                    emg = 0
                                    acc = 1
                                }
                            } else if (byteRead==11.toUByte() && start==1){
                                if(emg==1&&acc==0){
                                    val frame = decodeEmgFrame(readEmgBuffer.copyOfRange(0, readBufferPosition))
                                    queueHandOuter!!.giveMeTheEmgQueue().add(frame)
                                    if(queueHandOuter!!.ifEmgEnable())queueHandOuter!!.giveMeTheEmgQueuetoPlot().add(frame)
                                    emg=0
                                } else if(emg==0&&acc==1){
                                    val frame = decodeAccFrame(readAccBuffer.copyOfRange(0, readBufferPosition))
                                    queueHandOuter!!.giveMeTheAccQueue().add(frame)
                                    if(queueHandOuter!!.ifAccEnable())queueHandOuter!!.giveMeTheAccQueuetoPlot().add(frame)
                                    acc = 0
                                }
                                readBufferPosition=0
                                start=0
                            } else if (start == 1 && emg == 1 && acc == 0) {
                                readEmgBuffer[readBufferPosition++] = byteRead
                            } else if (start == 1 && emg == 0 && acc == 1) {
                                readAccBuffer[readBufferPosition++] = byteRead
                            }


                    }
                }
            } catch (ex: IOException) {
            stopThread = true
        }

        }
    })
    readDataThread.start()

}

    fun decodeAccFrame(data:UByteArray): AccFrame{
        val size = 12
        var change = 0
        var number = 0
        val encodedData= UByteArray(size)
        for(i in 0 until data.size){
            when {
                data[i]==10.toUByte() -> change=1
                change == 1 -> {
                    data[i]= data[i].inv()
                    encodedData[number]=data[i]
                    number++
                    change=0
                }
                else -> {
                    encodedData[number]=data[i]
                    number++
                }
            }
        }
        return AccFrame(encodedData)
    }

    fun decodeEmgFrame(data:UByteArray): EmgFrame{
        val size = 4
        var change = 0
        var number = 0
        val encodedData= UByteArray(size)
        for(i in 0 until data.size){
            when {
                data[i]==10.toUByte() -> change=1
                change == 1 -> {
                    data[i]= data[i].inv()
                    encodedData[number]=data[i]
                    number++
                    change=0
                }
                else -> {
                    encodedData[number]=data[i]
                    number++
                }
            }
        }
        return EmgFrame(encodedData)
    }

fun disconnect() {
    stopThread = true
    btInputStream?.close()
    myBluetoothSocket?.close()
}

interface IsReadyForTransmition {
    fun isDeviceReady(isReady: Boolean)
}
}