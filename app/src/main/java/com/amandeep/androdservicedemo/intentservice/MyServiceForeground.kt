package com.amandeep.androdservicedemo.intentservice

import android.app.IntentService
import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.ServiceCompat
import com.amandeep.androdservicedemo.MainActivity
import com.amandeep.androdservicedemo.MyApplication
import com.amandeep.androdservicedemo.jonserviceScheduler.MyNotificationManager
import java.lang.Exception
import kotlin.random.Random

/**
 * Intent service is depicated , if you are using 8 or + use jobIntentService
 * this one is run on worker thread so no need to start the thread
 * As we notice a we go on background it stops after two minutes.
 * */

class MyServiceForeground : IntentService("IntentService") {

    private var isGeneratedRandomNo = false;
    private val TAG = "MyIntentService"
    private var randomNumber = 0

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onHandleIntent(p0: Intent?) {
        Log.e(TAG, "onHandleIntent: ${Thread.currentThread().name}")
        startForeground(1 , getNotification())
        isGeneratedRandomNo = true
        if (isGeneratedRandomNo) {
            generateRandomNumber()
        }

    }

    private fun getNotification(): Notification? {

        val notification=  MyApplication.getMyAppsNotificationManager()?.getNotification(IntentServiceActivity::class.java,
            "BackgroundService running",
            1,
            false,
            1);
        return notification
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        stopGeneratingNumber()
        MyNotificationManager.getInstance(this)?.cancelNotification(1)
        super.onDestroy()
    }

    private fun stopGeneratingNumber() {
        isGeneratedRandomNo = false
    }


    private fun generateRandomNumber() {
        while (isGeneratedRandomNo) {
            try {
                Thread.sleep(1000)
                randomNumber = Random.nextInt(10) + 0
                Log.e(TAG, "generateRandomNumber:$randomNumber ")
            } catch (e: Exception) {
                Log.i(TAG, "generateRandomNumber: e")
            }
        }
    }


}