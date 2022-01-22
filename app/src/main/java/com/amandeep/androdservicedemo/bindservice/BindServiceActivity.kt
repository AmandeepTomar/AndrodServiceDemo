package com.amandeep.androdservicedemo.bindservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.telecom.ConnectionService
import com.amandeep.androdservicedemo.BIND_SERVICE_CODE
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.STARTED_SERVICE_CODE
import com.amandeep.androdservicedemo.databinding.ActivityBindServiceBinding

class BindServiceActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBindServiceBinding
    private  var serviceIntent: Intent?=null
    private var serviceConnection:ServiceConnection?=null
    private var isServiceBind=false
    private var myService:BindService?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBindServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
            serviceIntent=Intent(this,BindService::class.java)
        handleClicks()
    }

    private fun handleClicks() {
        binding.btnStartService.setOnClickListener {
            startService(serviceIntent)
        }

        binding.btnStopService.setOnClickListener {
            stopService(serviceIntent)
        }

        binding.btnBindService.setOnClickListener {
            bindServiceConnectionServiceCallBack()
        }

        binding.btnUnBindService.setOnClickListener {
            unBindCurrentService()
        }

        binding.btnGetNo.setOnClickListener {
            if(isServiceBind) {
                val no = myService?.getRandomNumber()

                binding.tvNumberBind.text = no.toString()
            }else{
                binding.tvNumberBind.text="Service is not bound"
            }
        }
    }

    private fun unBindCurrentService() {
        if (isServiceBind){
            serviceConnection?.let { unbindService(it) }
        }

    }

    private fun bindServiceConnectionServiceCallBack() {

        if (serviceConnection == null) {
            object : ServiceConnection {
                override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                     myService=(iBinder as? BindService.MyServiceBinder)?.getMyService()
                    isServiceBind=true;

                }
                override fun onServiceDisconnected(componentName: ComponentName) {
                        isServiceBind=false;
                }
            }.also { serviceConnection = it }

            serviceConnection?.let { bindService(serviceIntent, it,Context.BIND_AUTO_CREATE) }

        }

    }

    override fun onBackPressed() {
        val intent=Intent()
        setResult(BIND_SERVICE_CODE,intent)
        super.onBackPressed()
    }
}