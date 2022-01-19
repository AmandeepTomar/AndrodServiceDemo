package com.amandeep.androdservicedemo.startedService

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.STARTED_SERVICE_DATA
import com.amandeep.androdservicedemo.TAG
import com.amandeep.androdservicedemo.databinding.ActivityStartedServiceBinding

class StartedServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartedServiceBinding
    private var isButtonClicked=false
    private var randomNumber:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartedServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StartedService.liveDataNo.observe(this, Observer {
            if (isButtonClicked)
            binding.tvNumber.text=it.toString()

            isButtonClicked=false
        })

        handleClick()
    }


    private fun handleClick() {
        handleStartedServiceClicks()

    }

    // Handle started service clicks and init
    private fun handleStartedServiceClicks() {
        val startedService = Intent(this,StartedService::class.java).apply {
            putExtra(STARTED_SERVICE_DATA,"Started Service started")
        }

        binding.btnStartService.setOnClickListener {
            Log.e(TAG, "handleClick: MainActivity ${Thread.currentThread().id}")
            startService(startedService)

        }

        binding.btnStopService.setOnClickListener {
            stopService(startedService)
        }

        binding.btnGetNo.setOnClickListener {
            isButtonClicked=true

        }
    }
}