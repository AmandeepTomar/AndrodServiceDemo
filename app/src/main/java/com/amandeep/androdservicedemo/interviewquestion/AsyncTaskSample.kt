package com.amandeep.androdservicedemo.interviewquestion

import android.os.AsyncTask
import android.util.Log

/***
 * Q. What happen wen we call the same AsyncTask twice on the same instance.
 * Ans: App got crashed , with IlligalStateException.
 *
 *
 * */
class AsyncTaskSample(val count: Int) : AsyncTask<String, Int,String>() {

    override fun onPreExecute() {
        super.onPreExecute()
        Log.e("TAG", "onPreExecute: count $count", )
    }
    override fun doInBackground(vararg params: String?): String {
        Log.e("TAG", "doInBackground: count $count", )
        Thread.sleep(10000)
        return  " Result is received here"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.e("TAG", "onPostExecute:  $result")
    }
}