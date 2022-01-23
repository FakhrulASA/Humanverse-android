package com.humanverse.humanverseapp.feature.splash.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.feature.auth.ui.RegistrationActivity
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    /** Duration of wait  */
    private lateinit var auth: FirebaseAuth
    private lateinit var firebase : Firebase
    private var user = FirebaseAuth.getInstance().currentUser
    private val SPLASH_DISPLAY_LENGTH: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        firebase = Firebase
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            if(user!=null){
                val mainIntent = Intent(this, HomeActivity::class.java)
                startActivity(mainIntent)
                finish()
            }else{
                val mainIntent = Intent(this, RegistrationActivity::class.java)
                startActivity(mainIntent)
                finish()
            }

        }, SPLASH_DISPLAY_LENGTH)
    }



}