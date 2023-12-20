package com.amandeep.androdservicedemo

import android.app.Application
import com.amandeep.androdservicedemo.jonserviceScheduler.MyNotificationManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        myNotificationManager = MyNotificationManager.getInstance(this)
        myNotificationManager?.registerNotificationChannelChannel(
            "ChannelId",
            "BackgroundService",
            "BackgroundService"
        );
    }


    companion object {
        private var myNotificationManager: MyNotificationManager? = null
        fun getMyAppsNotificationManager(): MyNotificationManager? {
            return myNotificationManager

        }
    }

}