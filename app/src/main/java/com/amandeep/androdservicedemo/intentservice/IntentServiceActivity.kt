package com.amandeep.androdservicedemo.intentservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.TAG
import com.amandeep.androdservicedemo.databinding.ActivityIntentServiceBinding

class IntentServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntentServiceBinding
    private lateinit var myIntent:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntentServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myIntent=Intent(this,MyService::class.java)

        binding.btnIsStartService.setOnClickListener {
            Log.d(TAG, "onCreate: ${Thread.currentThread().name}")
            startService(myIntent)
        }

        binding.btnIsStopService.setOnClickListener {
            stopService(myIntent)
        }
    }
}