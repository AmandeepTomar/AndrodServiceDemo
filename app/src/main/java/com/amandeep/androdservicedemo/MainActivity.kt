package com.amandeep.androdservicedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.amandeep.androdservicedemo.bindingbetweentwoaaps.ServerSideActivity
import com.amandeep.androdservicedemo.bindservice.BindServiceActivity
import com.amandeep.androdservicedemo.databinding.ActivityMainBinding
import com.amandeep.androdservicedemo.intentservice.IntentServiceActivity
import com.amandeep.androdservicedemo.jonserviceScheduler.JobServiceActivity
import com.amandeep.androdservicedemo.startedService.StartedServiceActivity
import javax.security.auth.login.LoginException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClick()

    }

    private fun handleClick() {
        binding.btnStartService.setOnClickListener {
            Intent(this, StartedServiceActivity::class.java).apply {
                activityResultCallBack.launch(this)
            }
        }

        binding.btnBindServiceLocal.setOnClickListener {
            Intent(this, BindServiceActivity::class.java).apply {
                activityResultCallBack.launch(this)
            }
        }

        binding.btnBindServiceRemote.setOnClickListener {
            Intent(this, ServerSideActivity::class.java).apply {
                activityResultCallBack.launch(this)
            }
        }

        binding.btnIntentService.setOnClickListener {
            Intent(this, IntentServiceActivity::class.java).apply {
                activityResultCallBack.launch(this)
            }
        }

        binding.btnJobService.setOnClickListener {
            startActivity(Intent(this, JobServiceActivity::class.java))
        }

    }


    private val activityResultCallBack = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it?.let {
            when (it.resultCode) {
                STARTED_SERVICE_CODE ->
                    Log.i(TAG, "Started Service")

                BIND_SERVICE_CODE ->
                    Log.i(TAG, ":Bind Service ")

                SERVER_SERVICE_CODE ->
                    Log.i(TAG, "Server Service: ")

                else -> Log.i(TAG, ":else case ")
            }
        }
    }


}