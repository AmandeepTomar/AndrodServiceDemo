package com.amandeep.androdservicedemo.jonserviceScheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlin.random.Random


class MyJobService : JobService() {
private val TAG = "MyJobService"
    private var mRandomNumber = 0
    private var mIsRandomGeneratorOn = false

    private val MIN = 0
    private val MAX = 100
    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    /**
     * Return FALSE when this jpb is of short duration
     * and needs to be executed for very small time.
     * By default everything here runs on UI thread. If you don't
     * want to block the UI thread with long running work, then use thread.
     * Return TRUE whenever you are running long running tasks. So when you are
     * using a thread to do long running task, return true.
     * @param jobParameters
     * @return
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e(TAG, "onStartJob: ")
        doBackgroundWork()
        return true
    }

    private fun doBackgroundWork() {
        Thread {
            mIsRandomGeneratorOn = true
            startRandomNumberGenerator()
        }.start()
    }

    /**
     * This method gets called when job gets cancelled.
     * Return True if you want to restart the job automatically when a condition is met (WIFI - ON)
     * Return false if you want don't want to restart the job automatically even when the condition is met (WIFI - OFF)
     * @param jobParameters
     * @return
     */
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e(TAG, "onStopJob: ")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsRandomGeneratorOn = false
        Log.e(TAG, "onDestroy: ")
    }


    private fun startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = Random.nextInt(MAX) + MIN
                    Log.i(
                        TAG,
                        "Thread id: " + Thread.currentThread().id + ", Random Number: " + mRandomNumber
                    )
                }
            } catch (e: InterruptedException) {
                Log.i(TAG, "Thread Interrupted")
            }
        }
    }
}