package com.humanverse.humanverseapp.feature.home.ui.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityHomeBinding
import com.humanverse.humanverseapp.util.ExtenFun.isNetworkAvailable
import com.humanverse.humanverseapp.util.showTrialDialog

class HomeActivity : BaseActivity() {
    var db = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db.collection("trialstatus")
            .whereEqualTo("email", auth.currentUser!!.email)
            .get()
            .addOnSuccessListener {
                showTrialDialog(it.documents.get(0).get("time").toString(),this){

                }
            }
            .addOnFailureListener { e ->

            }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}