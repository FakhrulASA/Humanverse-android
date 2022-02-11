package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityOrderServiceBinding

class OrderServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button10.setOnClickListener{
            startActivity(Intent(this,OrderProgressActivity::class.java))
        }

        val b: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner,
            resources.getStringArray(R.array.size_list)
        )
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner3.adapter = b
    }
}