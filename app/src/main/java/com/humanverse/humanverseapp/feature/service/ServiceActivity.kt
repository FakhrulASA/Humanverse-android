package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.humanverse.humanverseapp.databinding.ActivityServiceBinding
import com.humanverse.humanverseapp.model.ServiceModel


class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding
    private lateinit var adapter: ServiceItemAdapter
    var db = FirebaseFirestore.getInstance()
    var dataList: MutableList<ServiceModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db.collection("/service/car_wash/carwash_01")
            .get()
            .addOnSuccessListener { documents ->
                Log.w("DATA FOUND FRB", documents.toString())
                for (document in documents) {
                    dataList.add(
                        ServiceModel(
                            img = document.get("img").toString(),
                            title = document.get("title").toString(),
                            des = document.get("des").toString(),
                            price = document.get("price").toString().toInt()
                        )
                    )
                }

                adapter = ServiceItemAdapter(this)
                adapter.submitListData(dataList)
                val layoutManager = GridLayoutManager(
                    this, 1, GridLayoutManager.VERTICAL, false
                )
                binding.recService.layoutManager = layoutManager
                binding.recService.adapter = adapter
                adapter.itemActionListener = {
                    startActivity(Intent(this, ServiceActivity::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DATA FOUND FRB", "Error getting documents: ", exception)
            }

    }
}