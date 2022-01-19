package com.amandeep.androdservicedemo.startedService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amandeep.androdservicedemo.TAG
import java.lang.Exception
import kotlin.random.Random

class StartedService : Service() {

    private var isRandomNoGenerated=false
    private var min=0
    private var max=100
    private var randomNumber=0

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.e(TAG, "onBind: ")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRandomNoGenerated=true
        Thread(Runnable {
            startGeneratedRandomNo()
        }).start()

        return START_NOT_STICKY // not recreate
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: ")
        stopRandomNumberGenerated()
        super.onDestroy()
    }

    private fun stopRandomNumberGenerated() {
        isRandomNoGenerated=false
    }

    // method to get the random no
        private fun startGeneratedRandomNo(){
            while (isRandomNoGenerated){
                try {
                    Thread.sleep(1000)
                    if (isRandomNoGenerated){
                        randomNumber= Random.nextInt(max)+min
                        liveDataNo.postValue(randomNumber)
                        Log.e(TAG, "${Thread.currentThread().id}: Random No $randomNumber")
                    }
                }catch (ex:Exception){
                    Log.e(TAG, "startGeneratedRandomNo: $ex")
                }
            }
        }


    companion object{
        val liveDataNo = MutableLiveData<Int>()
    }
}