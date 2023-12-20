package com.amandeep.androdservicedemo.jonserviceScheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.databinding.ActivityJobServiceBinding
import com.amandeep.androdservicedemo.intentservice.MyJobIntentService

class JobServiceActivity : AppCompatActivity() {

    private var _binding: ActivityJobServiceBinding? = null
    private val binding get() = _binding

    private val jobScheduler: JobScheduler? by lazy { getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler? }
    private val TAG = "JobServiceActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityJobServiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnIsStartService?.setOnClickListener {
            startJob()
        }
        binding?.btnIsStopService?.setOnClickListener {
            stopJob()
        }
    }

    private fun startJob() {
        val componentName = ComponentName(this, MyJobService::class.java)
        val jonInfo = JobInfo.Builder(101, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 60 * 1000)
            .setRequiresCharging(false)
            .setPersisted(true)
            .build()

        if (jobScheduler?.schedule(jonInfo) == JobScheduler.RESULT_SUCCESS) {
            Log.i(
                TAG,
                "MainActivity thread id: " + Thread.currentThread()
                    .getId() + ", job successfully scheduled"
            );
        } else {
            Log.i(
                TAG,
                "MainActivity thread id: " + Thread.currentThread()
                    .getId() + ", job could not be scheduled"
            );
        }
    }

    private fun stopJob() {
        jobScheduler?.cancel(101)
    }

}