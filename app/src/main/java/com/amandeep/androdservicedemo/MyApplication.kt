package com.amandeep.androdservicedemo

import android.app.Application
import com.amandeep.androdservicedemo.jonserviceScheduler.MyNotificationManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        myNotificationManager?.registerNotificationChannelChannel(
            "ChannelId",
            "BackgroundService",
            "BackgroundService"
        );
    }


    companion object {
        private val myNotificationManager: MyNotificationManager? = null
        fun getMyAppsNotificationManager(): MyNotificationManager? {
            return myNotificationManager

        }
    }

}