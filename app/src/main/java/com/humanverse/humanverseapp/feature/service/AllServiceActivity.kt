package com.humanverse.humanverseapp.feature.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityAllServiceBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapter
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapterAll
import com.humanverse.humanverseapp.model.ModelDashboardItem
import com.humanverse.humanverseapp.util.SERVICE_TPYE
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.showLoader

class AllServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllServiceBinding
    private lateinit var adapter: DashboardItemAdapterAll
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    var dashboardItems : MutableList<ModelDashboardItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoader("Loading, please wait...", this)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        adapter = DashboardItemAdapterAll(this)
        db.collection("category")
            .get()
            .addOnSuccessListener {
                hideConsent()
                for(document in it.documents){
                    dashboardItems.add(
                        ModelDashboardItem(
                            document.get("title").toString(),
                            document.get("image").toString(),
                            document.get("name").toString()
                        )
                    )
                    dashboardItems.sortBy {
                        it.title
                    }
                }
                adapter.submitListData(dashboardItems)
                val layoutManager = GridLayoutManager(
                    this, 3, GridLayoutManager.VERTICAL, false
                )
                binding.recyclerView.layoutManager = layoutManager
                binding.recyclerView.adapter = adapter
                adapter.itemActionListener = {
                    val intent = Intent(this, ServiceActivity::class.java)
                    intent.putExtra(SERVICE_TPYE,it.type)
                    intent.putExtra("NAME",it.title)
                    startActivity(intent)
                }

                hideConsent()
            }
            .addOnFailureListener { e ->
                hideConsent()
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()

            }
    }
}