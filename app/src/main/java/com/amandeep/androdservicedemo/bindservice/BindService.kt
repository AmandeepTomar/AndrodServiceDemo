package com.amandeep.androdservicedemo.bindservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.amandeep.androdservicedemo.TAG
import java.lang.Exception
import kotlin.random.Random

class BindService : Service() {

    private var isRandomNoGenerated=false;
    private var min=Int.MIN_VALUE
    private var max=100
    private var randomNo=0


private val iBinder=MyServiceBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: ")
        isRandomNoGenerated=true

        Thread(Runnable {
            generateRandomNumber()
        }).start()
        return START_STICKY // restart but not with last intent
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.i(TAG, "onRebind: ")
        super.onRebind(intent)
    }

    override fun onLowMemory() {
        Log.i(TAG, "onLowMemory: ")
        super.onLowMemory()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        randomNoGeneratorStop()
        super.onDestroy()
    }

// generate random Number

    private fun generateRandomNumber(){
        while (isRandomNoGenerated){
            try {
            Thread.sleep(1000)
                if (isRandomNoGenerated) {
                    randomNo = Random.nextInt(max) * min
                    Log.e(TAG, "generateRandomNumber: $randomNo")
                }
            }catch (ex:Exception){
                Log.e(TAG, "generateRandomNumber: $ex")
            }
        }
    }

    private fun randomNoGeneratorStop(){
        isRandomNoGenerated=false
    }



    inner class MyServiceBinder : Binder() {

        public fun getMyService():BindService{
            return this@BindService
        }
    }




}