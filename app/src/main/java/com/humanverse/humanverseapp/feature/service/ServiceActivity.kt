package com.humanverse.humanverseapp.feature.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.humanverse.humanverseapp.databinding.ActivityServiceBinding
import com.humanverse.humanverseapp.model.ServiceModel
import com.humanverse.humanverseapp.util.*
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.util.ExtenFun.isNetworkAvailable


class ServiceActivity : BaseActivity() {
    private lateinit var binding: ActivityServiceBinding
    private lateinit var adapter: ServiceItemAdapter
    var db = FirebaseFirestore.getInstance()
    var dataList: MutableList<ServiceModel> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressBar2.visibility= View.GONE
        when (intent.extras!!.get(SERVICE_TPYE) as Int) {
            SERVICE_AC -> {
                getService("acrepair")
                title = "Ac services"
            }
            SERVICE_CAR_WASH -> {
                getService("carwash")
                title = "Car wash service's"

            }
            SERVICE_CALENDER -> {
                getService("calender")
                title = "Calender service's"

            }
            SERVICE_CAR_RENT -> {
                getService("carrental")
                setTitle("Car rental service's")
            }
            SERVICE_CHARITIES -> {
                getService("charities")
                setTitle("Charities service's")
            }
            SERVICE_CAR_REPAIR -> {
                getService("carrepair")
                setTitle("Car repair service's")
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getService(servie: String) {
        binding.progressBar2.visibility= View.VISIBLE

        if(isNetworkAvailable()){
            db.collection("/services/")
                .whereEqualTo("category",servie)
                .get()
                .addOnSuccessListener { documents ->
                    binding.progressBar2.visibility= View.GONE
                    if(documents.size()==0){
                        binding.imageView10.visibility=View.VISIBLE
                    }
                    Log.w("DATA FOUND FRB", documents.toString())
                    for (document in documents) {
                        dataList.add(
                            ServiceModel(
                                img = document.get("banner").toString(),
                                title = document.get("serviceName").toString(),
                                des = document.get("description").toString(),
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
                    binding.progressBar2.visibility= View.GONE
                    binding.imageView10.visibility=View.VISIBLE
                    Log.w("DATA FOUND FRB", "Error getting documents: ", exception)
                }
        }else{
            showAlertDialog(this, "No internet", "Please check your internet connection")
        }

    }


}