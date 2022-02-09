package com.humanverse.humanverseapp.feature.service

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityAddServiceBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.util.FileUtil
import com.humanverse.humanverseapp.util.Utils
import java.io.File


class AddServiceActivity : BaseActivity() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private lateinit var country: String
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var area: String
    private lateinit var serviceName: String
    private var imageUploaded: Boolean = false
    private lateinit var description: String
    private lateinit var price: String
    private lateinit var website: String
    private lateinit var category: String

    private lateinit var file: File
    private lateinit var binding: ActivityAddServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        binding.button4.setOnClickListener {
            openGallery()
        }
        ref = storageReference?.child(
            "service_banner/" + auth.currentUser!!.email
        )!!
        binding.button5.setOnClickListener {
            category = binding.spinner.selectedItem.toString()
            country = binding.spinner4.selectedItem.toString()
            state = binding.spinner5.selectedItem.toString()
            city = binding.editTextTextPersonName4.text.toString()
            website = binding.editTextTextPersonName5.text.toString()
            price = binding.editTextTextPersonName6.text.toString()
            description = binding.editTextTextPersonName26.text.toString()
            serviceName = binding.spinner2.text.toString()
            validateInfo()
        }
    }

    private fun validateInfo() {
        if (category.isEmpty() || binding.spinner.selectedItemPosition == 0) {
            showAlertDialog(
                this,
                "Category is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (country.isEmpty() || binding.spinner4.selectedItemPosition == 0) {
            showAlertDialog(
                this,
                "Country is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (state.isEmpty() || binding.spinner5.selectedItemPosition == 0) {
            showAlertDialog(
                this,
                "State is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (city.isEmpty()) {
            showAlertDialog(
                this,
                "City is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (price.isEmpty()) {
            showAlertDialog(
                this,
                "Price is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (description.isEmpty()) {
            showAlertDialog(
                this,
                "Description is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (serviceName.isEmpty()) {
            showAlertDialog(
                this,
                "Service name/title is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else if (!imageUploaded) {
            showAlertDialog(
                this,
                "Image/banner name/title is required",
                "Please fill the field with proper value in order to complete registration"
            )
        } else {
            submitData()
        }
    }

    lateinit var image: String

    private fun submitData() {
        binding.progressBar3.visibility = View.VISIBLE
        val uploadTask = ref?.putFile(file.toUri())
        try {
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                ref.downloadUrl.addOnSuccessListener {
                    image = it.toString()
                    val service = hashMapOf(
                        "serviceName" to serviceName,
                        "category" to category,
                        "country" to country,
                        "state" to state,
                        "description" to description,
                        "price" to price,
                        "city" to city,
                        "email" to auth.currentUser!!.email.toString(),
                        "banner" to image
                    )
                    try {
                        db.collection("service")
                            .document("services")
                            .collection(category.lowercase().filter { !it.isWhitespace() })
                            .document(auth.currentUser!!.email!!.toString())
                            .set(service)
                            .addOnSuccessListener {
                                Utils.showAlertDialogForTap(this,
                                    "Successfully posted",
                                    "Your service successfully posted, wait till the verification is complete!",
                                    {
                                        startActivity(Intent(this, HomeActivity::class.java))
                                        finish()
                                    },
                                    {

                                    })
                                binding.progressBar3.visibility = View.GONE

                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                                binding.progressBar3.visibility = View.GONE

                            }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    binding.progressBar3.visibility = View.GONE
                }

            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val pickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            val mimetypes = arrayOf("image/*", "application/pdf/*")
            putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        }
        intentDocument.launch(pickIntent)
    }

    private var intentDocument =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                file = FileUtil.from(this, result.data?.data!!)
                if (result.resultCode == Activity.RESULT_OK) {
                    imageUploaded = true
                }
            } else {
            }
        }

    private fun initSpinner() {
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