package com.amandeep.androdservicedemo.intentservice

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import java.lang.Exception

/**
 * 1. onStopCurrentWork() -> true You restart the service , false means you are not care about
 * reschedule or restart the service
 *  permission ->android.permission.BIND_JOB_SERVICE
 *
 *onHandleWork(intent Intent){}
 *  add wake lock permission in manifest -> to avoid stop service in background.
 *
 * */

class MyJobIntentService : JobIntentService() {
    private val TAG = "MyJobIntentService"
    private var randomNumber = 0

    companion object {
        fun enqueueWorkStart(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, 101, work)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onHandleWork(intent: Intent) {
        Log.e(TAG, "onHandleWork: ${Thread.currentThread().name}")
        generateRandomNumber(intent.getIntExtra("Count",0))
    }

    override fun onStopCurrentWork(): Boolean {
        Log.e(TAG, "onStopCurrentWork: ")
        return super.onStopCurrentWork()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }

    private fun generateRandomNumber(count: Int) {
        for (i in 1..10) {
            try {
                Thread.sleep(1000)
                Log.e(TAG, "generateRandomNumber: $i Started At $count")
            } catch (e: Exception) {
                Log.e(TAG, "generateRandomNumber: $e")
            }
        }
    }
}