package com.humanverse.humanverseapp.feature.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityAddServiceBinding

import android.widget.ArrayAdapter




class AddServiceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner,
            resources.getStringArray(R.array.service_type)
        )
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = a
        val b: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner,
            resources.getStringArray(R.array.states)
        )
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner5.adapter = b

        val c: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner,
            resources.getStringArray(R.array.country_list)
        )
        c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner4.adapter = c

    }
}