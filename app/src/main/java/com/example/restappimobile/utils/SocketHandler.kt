package com.example.restappimobile.utils

import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException
import kotlin.math.log

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket(){
        try {
            mSocket = IO.socket("http://192.168.1.64:3000/orders/delivery")
        }catch (e: URISyntaxException){
            Log.d("Error", "No se pudo conectar el sokcet ${e.message}")
        }
    }

    @Synchronized
    fun getSocket(): Socket{
        return mSocket
    }

    @Synchronized
    fun establishConnection(){
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection(){
        mSocket.disconnect()
    }

}