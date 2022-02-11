package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select desired date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        binding.textView32.setOnClickListener {
           datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        }
        binding.textVi2ew32.setOnClickListener {
            materialTimePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
            binding.textView32.text=datePicker.headerText
        }
        materialTimePicker.addOnPositiveButtonClickListener {
            binding.textVi2ew32.text = materialTimePicker.hour.toString()+":"+materialTimePicker.minute.toString()
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