package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

        } else {
            if (binding.textLayoutPass.text!!.length < 6) {
                binding.textLayoutPass.error = "Please give valid password"
            } else {
                auth.signInWithEmailAndPassword(binding.textLayoutEmail.text.toString().trim(), binding.textLayoutPass.text.toString().trim())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, HomeActivity::class.java))
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