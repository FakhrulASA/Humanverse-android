package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityLoginBinding
import com.humanverse.humanverseapp.databinding.ActivityRegistrationBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.firebase.AuthImp

class RegistrationActivity : BaseActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authImp: AuthImp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        authImp = AuthImp()
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            verifyRegistration()
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun verifyRegistration() {
        authImp.firebaseRegistration(
            binding.regTlEmail.text.toString().trim(),
            binding.regTlConPass.text.toString().trim(),
            auth,
            this,
            {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, {
                makeToastLong(it)
            })
    }
}