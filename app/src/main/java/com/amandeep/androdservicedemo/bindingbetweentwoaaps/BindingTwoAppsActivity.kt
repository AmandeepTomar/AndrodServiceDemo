package com.amandeep.androdservicedemo.bindingbetweentwoaaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amandeep.androdservicedemo.R
import com.amandeep.androdservicedemo.databinding.ActivityBindingTwoAppsBinding

class BindingTwoAppsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindingTwoAppsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBindingTwoAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClickEvents()
    }

    private fun handleClickEvents() {


    }
}