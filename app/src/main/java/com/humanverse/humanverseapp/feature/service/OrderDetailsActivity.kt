package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.databinding.ActivityOrderDetailsBinding
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.showLoader

class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsBinding
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    var email : String =""
    var name: String = ""
    var price: String = ""
    var image: String = ""
    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        name = intent.extras!!.get("NAME").toString()
        price = intent.extras!!.get("PRICE").toString()
        image = intent.extras!!.get("IMAGE").toString()
        id = intent.extras!!.get("ID").toString()

        binding.button11.setOnClickListener {
            var intent = Intent(this, OrderServiceActivity::class.java)
            intent.putExtra("ID",id)
            intent.putExtra("NAME",name)
            intent.putExtra("IMAGE",image)
            intent.putExtra("PRICE",price)
            intent.putExtra("EMAIL",email)
            startActivity(intent)
        }
        showLoader("Loading service, wait...",this)

        Glide
            .with(this)
            .load(image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView8)

        db.collection("/services/")
            .document(id)
            .get()
            .addOnSuccessListener { documents ->
                hideConsent()
                binding.textView44.text = documents.get("country").toString()
                binding.textView45.text = documents.get("state").toString()
                binding.textView46.text = documents.get("city").toString()
                binding.textView48.text =documents.get("serviceName").toString()
                binding.textView49.text =documents.get("description").toString()
                binding.textView50.text = "Website: "+
                 if(documents.get("serviceWebsite").toString()==null||documents.get("serviceWebsite").toString()==""){
                     "No website given"
                 }else{
                     documents.get("serviceWebsite").toString()
                 }
                binding.textView51.text ="Price starts from: "+ documents.get("price").toString()+"$"
                binding.textView39.text ="Contact: "+ documents.get("email").toString()

                email = documents.get("email").toString()
            }
            .addOnFailureListener { exception ->
                hideConsent()
                Log.w("DATA FOUND FRB", "Error getting documents: ", exception)
            }
    }
}