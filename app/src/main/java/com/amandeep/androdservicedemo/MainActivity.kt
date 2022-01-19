package com.amandeep.androdservicedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amandeep.androdservicedemo.bindservice.BindServiceActivity
import com.amandeep.androdservicedemo.databinding.ActivityMainBinding
import com.amandeep.androdservicedemo.startedService.StartedService
import com.amandeep.androdservicedemo.startedService.StartedServiceActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClick()

    }

    private fun handleClick() {
        binding.btnStartService.setOnClickListener {
            Intent(this,StartedServiceActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnBindServiceLocal.setOnClickListener {
            Intent(this,BindServiceActivity::class.java).apply {
                startActivity(this)
            }
        }

    }


}