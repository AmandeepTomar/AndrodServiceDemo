package com.amandeep.androdservicedemo.bindservice

import android.content.ComponentName
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.databinding.ActivityBindServiceBinding

class BindServiceActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBindServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBindServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClicks()
    }

    private fun handleClicks() {
        binding.btnStartService.setOnClickListener {

        }

        binding.btnStopService.setOnClickListener {

        }

        binding.btnBindService.setOnClickListener {
            bindServiceConnectionServiceCallBack()
        }

        binding.btnUnBindService.setOnClickListener {
            unBindCurrentService()
        }

        binding.btnGetNo.setOnClickListener {

        }
    }

    private fun unBindCurrentService() {

    }

    private fun bindServiceConnectionServiceCallBack() {
        val serviceCallback= object : ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                TODO("Not yet implemented")
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                TODO("Not yet implemented")
            }

        }
    }
}