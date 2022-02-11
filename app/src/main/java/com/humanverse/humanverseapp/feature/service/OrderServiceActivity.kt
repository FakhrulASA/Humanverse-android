package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityOrderServiceBinding

class OrderServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderServiceBinding

    var name: String = ""
    var price: String = ""
    var image: String = ""
    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button10.setOnClickListener {
            startActivity(Intent(this, OrderProgressActivity::class.java))
        }
        name = intent.extras!!.get("NAME").toString()
        price = intent.extras!!.get("PRICE").toString()
        image = intent.extras!!.get("IMAGE").toString()
        id = intent.extras!!.get("ID").toString()
        binding.textView24.text = name


        var priceX: Double = price.toDouble()/4
        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0){
                    binding.textView38.text = "$price$"
                }else if(position==1){
                    binding.textView38.text = (price.toInt()+priceX).toString()+"$"
                }else if(position==2){
                    binding.textView38.text = (price.toInt()+(priceX*2)).toString()+"$"
                }else if(position==3){
                    binding.textView38.text = (price.toInt()+(priceX*3)).toString()+"$"
                }
            }
        }
        Glide
            .with(this)
            .load(image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView16)
        timeDatePicker()
        setSpinner()
    }

    private fun timeDatePicker() {
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
            binding.textView32.text = datePicker.headerText
        }
        materialTimePicker.addOnPositiveButtonClickListener {
            binding.textVi2ew32.text =
                materialTimePicker.hour.toString() + ":" + materialTimePicker.minute.toString()
        }
    }

    private fun setSpinner() {
        val b: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_spinner,
            resources.getStringArray(R.array.size_list)
        )
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner3.adapter = b
    }
}