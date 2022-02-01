package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityLoginBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.firebase.AuthImp


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authImp: AuthImp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.progressBar.visibility= View.GONE
        authImp = AuthImp()
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            binding.progressBar.visibility= View.VISIBLE
            binding.button.text=""
            verifyInputs()
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {

    }

    private fun verifyInputs() {
        if (binding.textLayoutEmail.text.isNullOrEmpty()) {
            binding.textLayoutEmail.error = "Please give valid email"
            binding.progressBar.visibility= View.GONE
            binding.button.text="LOGIN"
        } else {
            if (binding.textLayoutPass.text!!.length < 6) {
                binding.textLayoutPass.error = "Please give valid password"
                binding.progressBar.visibility= View.GONE
                binding.button.text="LOGIN"
            } else {
                auth.signInWithEmailAndPassword(binding.textLayoutEmail.text.toString().trim(), binding.textLayoutPass.text.toString().trim())
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            if(auth.currentUser!!.isEmailVerified){
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }else{
                                showAlertDialog(this,"Verify your email!","Please verify your email address. We have sent a link in your mail")
                                binding.progressBar.visibility= View.GONE
                                binding.button.text="LOGIN"
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            makeToastLong(task.exception!!.message.toString())
                            binding.progressBar.visibility= View.GONE
                            binding.button.text="LOGIN"
                        }
                    }
            }
        }

    }
}