package com.amandeep.androdservicedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.amandeep.androdservicedemo.IMyAidlInterface
import kotlin.random.Random

class AIDLService : Service() {

    private val stubBinder = object : IMyAidlInterface.Stub() {
        override fun getMessge(): String {
            val randomNo = Random.nextInt(0)+10
            Log.e("TAG", "getMessge: $randomNo")
            return "Next Generated no is $randomNo"
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return stubBinder
    }

}