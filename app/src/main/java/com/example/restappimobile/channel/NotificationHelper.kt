package com.example.restappimobile.channel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.icu.text.CaseMap.Title
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.restappimobile.R

class NotificationHelper(base: Context) : ContextWrapper (base) {

    private val CHANNEL_ID = "com.example.restappimobile"
    private val CHANNEL_NAME = "RestAppIMobile"

    private var manager: NotificationManager? = null

    init{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //ES IGUAL O SUPERIOR A ANDROID OREO
            createChannel()
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.lightColor = Color.WHITE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(notificationChannel)
    }

    fun getManager(): NotificationManager{
        if (manager == null){
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return manager as NotificationManager
    }

    fun getNotification(title: String, body: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
             .setContentTitle( title )
             .setContentText(body)
             .setAutoCancel(true)
             .setColor(Color.GRAY)
             .setSmallIcon(R.mipmap.ic_launcher)
             .setStyle(NotificationCompat.BigTextStyle().bigText(body).setBigContentTitle(title))
    }

}