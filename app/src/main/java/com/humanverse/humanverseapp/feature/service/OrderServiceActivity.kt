package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityOrderServiceBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.hideDialog
import com.humanverse.humanverseapp.util.showConsent
import com.humanverse.humanverseapp.util.showLoader

class OrderServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderServiceBinding

    var name: String = ""
    var price: String = ""
    var image: String = ""
    var id: String = ""
    var email: String = ""
    private var size : String =""
    private var model : String =""
    private var date : String =""
    private var time : String =""
    private var address : String =""
    private var mobile : String =""
    private var description : String =""
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

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
        email = intent.extras!!.get("EMAIL").toString()
        binding.textView24.text = name
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
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
        binding.button10.setOnClickListener {
            showLoader("Request processing!\nPlease wait...", this)
            if(validateInput()){
                val service = hashMapOf(
                    "customer" to auth.currentUser!!.email,
                    "provider" to email,
                    "price" to price,
                    "size" to size,
                    "description" to description,
                    "model" to price,
                    "address" to address,
                    "time" to time,
                    "date" to date,
                    "serviceId" to id,
                    "status" to "1",
                    "paidamount" to "",
                    "userMobile" to mobile,
                    "review" to "",
                    "problem" to "",
                    "serviceName" to name
                )
                try {

                    db.collection("order")
                        .document()
                        .set(service)
                        .addOnSuccessListener {
                            showConsent("Your request has been submitted, wait provider to contact you!", this, false){
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }
                            hideConsent()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                            hideConsent()

                        }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                hideConsent()
            }

        }

    }

    private fun validateInput():Boolean {
        size = binding.spinner3.selectedItem.toString()
        if(binding.editTextTextPersonName3.text.isNullOrEmpty()){
            showConsent("Please insert model/serial no/type",this,true){
                hideDialog()            }
            return false
        }else{
            model=binding.editTextTextPersonName3.text.toString()
        }

        if(binding.textView32.text.isNullOrEmpty()){
            showConsent("Please your desired date",this,true){
                hideDialog()
            }
            return false

        }else{
            date=binding.textView32.text.toString()
        }

        if(binding.textVi2ew32.text.isNullOrEmpty()){
            showConsent("Please insert your desired time",this,true){
                hideDialog()
            }
            return false
        }else{
            time=binding.textVi2ew32.text.toString()
        }
        if(binding.textVi2e1w32.text.isNullOrEmpty()){
            showConsent("Please give your right address",this,true){
                hideDialog()
            }
            return false
        }else{
            address=binding.textVi2e1w32.text.toString()
        }


        if(binding.textView41.text.length<8){
            showConsent("Please give your mobile number.",this,true){
                hideDialog()
            }
            return false
        }else{
            mobile=binding.safasf.text.toString()
        }

        if(binding.safasf.text.length<50){
            showConsent("Please give your work description properly.",this,true){
                hideDialog()
            }
            return false
        }else{
            description=binding.safasf.text.toString()
        }
        return true
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