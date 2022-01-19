package com.amandeep.androdservicedemo.bindingbetweentwoaaps

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.amandeep.androdservicedemo.TAG
import java.lang.Exception
import kotlin.random.Random

class BindingTwoAppsService : Service() {
    private var isRandomNoGenerator=false
    private var randomNumber=0

    override fun onCreate() {
        Log.i(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRandomNoGenerator=true

        return START_STICKY
    }

    override fun onRebind(intent: Intent?) {
        Log.i(TAG, "onRebind: ")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onLowMemory() {
        Log.i(TAG, "onLowMemory: ")
        super.onLowMemory()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        stopGeneratingRandomNumber()
        super.onDestroy()
    }

    private fun startGeneratingRandomNumber(){
        while (isRandomNoGenerator){
            try{
                if (isRandomNoGenerator){
                    randomNumber= Random.nextInt(100)+0
                    Log.e(TAG, "RandomNumber: $randomNumber")
                }
            }catch (ex:Exception){
                Log.e(TAG, "startGeneratingRandomNumber: $ex")
            }
        }
    }

    public fun getRandomNumber(): Int {
        return randomNumber
    }

    private fun stopGeneratingRandomNumber(){
        isRandomNoGenerator=false
    }


    private class RandomNumberRequestHandler : Handler(){

    }
}