package com.amandeep.androdservicedemo.jonserviceScheduler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.amandeep.androdservicedemo.R


class MyNotificationManager constructor(private val context: Context) {

    private var notificationManagerCompat: NotificationManagerCompat? = null
    private var notificationManager: NotificationManager? = null

    private fun MyAppsNotificationManager(context: Context) {
        notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object{
        @Volatile  var instance: MyNotificationManager? = null
        fun getInstance(context: Context): MyNotificationManager? {
            if (instance == null) {
                instance = MyNotificationManager(context)
            }
            return instance
        }
    }


    fun registerNotificationChannelChannel(
        channelId: String?,
        channelName: String?,
        channelDescription: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = channelDescription
            val notificationManager = context!!.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun getNotification(
        targetNotificationActivity: Class<*>?,
        title: String?,
        priority: Int,
        autoCancel: Boolean,
        notificationId: Int
    ): Notification? {
        val intent = Intent(context, targetNotificationActivity)
        val pendingIntent = PendingIntent.getActivity(
            context, 1, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context!!, "ChannelID")
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context!!.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId("123")
                .setAutoCancel(true)
        return builder.build()
    }

    fun cancelNotification(notificationId: Int) {
        notificationManager!!.cancel(notificationId)
    }
}