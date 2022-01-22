package com.amandeep.androdservicedemo.bindingbetweentwoaaps

import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.SERVER_SERVICE_CODE
import com.amandeep.androdservicedemo.STARTED_SERVICE_CODE
import com.amandeep.androdservicedemo.databinding.ActivityBindingTwoAppsBinding

class ServerSideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindingTwoAppsBinding
    private lateinit var myIntent: Intent
    private lateinit var myService:ServerSideService
    private var serviceConnection:ServiceConnection?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBindingTwoAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myIntent= Intent(this,ServerSideService::class.java)

        handleClickEvents()

    }

    private fun handleClickEvents() {
        binding.btnStartServerService.setOnClickListener {
            startService(myIntent)
        }

        binding.btnStopServerService.setOnClickListener {
            stopService(myIntent)
        }

    }

    override fun onBackPressed() {
        val intent=Intent()
        setResult(SERVER_SERVICE_CODE,intent)
        super.onBackPressed()
    }
}