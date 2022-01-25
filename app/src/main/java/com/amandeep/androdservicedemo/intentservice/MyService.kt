package com.amandeep.androdservicedemo.intentservice

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.lang.Exception
import kotlin.random.Random

/**
 * Intent service is depicated , if you are using 8 or + use jobIntentService
 * this one is run on worker thread so no need to start the thread
 * As we notice a we go on background it stops after two minutes.
 * */

class MyService : IntentService("IntentService") {

    private var isGeneratedRandomNo=false;
    private val TAG="MyIntentService"
    private var randomNumber=0

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(p0: Intent?) {
        Log.e(TAG, "onHandleIntent: ${Thread.currentThread().name}" )
        isGeneratedRandomNo=true
            if (isGeneratedRandomNo){
                generateRandomNumber()
            }

    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        stopGeneratingNumber()
        super.onDestroy()
    }

    private fun stopGeneratingNumber() {
        isGeneratedRandomNo=false
    }


    private fun generateRandomNumber(){
        while (isGeneratedRandomNo){
            try {
                Thread.sleep(1000)
                randomNumber= Random.nextInt(10)+0
                Log.e(TAG, "generateRandomNumber:$randomNumber ")
            }catch (e:Exception){
                Log.i(TAG, "generateRandomNumber: e")
            }
        }
    }


}