package com.amandeep.androdservicedemo.bindingbetweentwoaaps

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import com.amandeep.androdservicedemo.TAG
import com.amandeep.androdservicedemo.utils.showToast
import java.lang.Exception
import kotlin.random.Random

class ServerSideService : Service() {
    private var isRandomNoGenerator=false
    private var randomNumber=0
    private val GET_COUNT=0

    override fun onCreate() {
        Log.i(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return if (intent?.`package`=="com.amandeep.clientsidebinderapp") {
            randomMessenger.binder
        } else {
            showToast("Wrong package")
            null
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRandomNoGenerator=true
        Thread(Runnable {
            startGeneratingRandomNumber()
        }).start()

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
                Thread.sleep(1000)
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


    private val randomMessenger = Messenger(RandomNumberRequestHandler())

    inner class RandomNumberRequestHandler : Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                GET_COUNT->{
                    val messageGetRandomNumber=Message.obtain(null,GET_COUNT)
                    messageGetRandomNumber.arg1=getRandomNumber()
                    try {
                        msg.replyTo.send(messageGetRandomNumber)
                    }catch (e: Exception){

                    }
                }
            }
            super.handleMessage(msg)
        }
    }
}