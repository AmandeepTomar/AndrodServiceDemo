package com.amandeep.androdservicedemo.utils

import android.content.Context
import android.widget.Toast

inline fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}