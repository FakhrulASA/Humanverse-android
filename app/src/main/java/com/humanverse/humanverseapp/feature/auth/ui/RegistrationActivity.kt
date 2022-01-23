package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.humanverse.humanverseapp.base.BaseActivity
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
            binding.progressBar.visibility = View.VISIBLE
            binding.button.text = ""
            verifyRegistration()
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun verifyRegistration() {
        if (binding.regTlEmail.text.isNullOrEmpty() || binding.regTlMobile.text.isNullOrEmpty() || binding.regTlConPass.text.isNullOrEmpty() || binding.regTlPass.text.isNullOrEmpty()) {
            makeToastLong("Please give proper input")
            binding.progressBar.visibility= View.GONE
            binding.button.text="REGISTER"
        } else {
            if (binding.regTlConPass.text.toString().trim() == binding.regTlPass.text.toString().trim()) {
                auth.createUserWithEmailAndPassword(
                    binding.regTlEmail.text.toString().trim(),
                    binding.regTlConPass.text.toString().trim()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                                // Sign in success, update UI with the signed-in user's information
                                if (it.isSuccessful) {
                                    startActivity(Intent(this, HomeActivity::class.java))
                                } else {
                                    makeToastLong(it.exception!!.message.toString())
                                    binding.progressBar.visibility= View.GONE
                                    binding.button.text="REGISTER"
                                }
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(
                                this.toString(),
                                "createUserWithEmailAndPassword:failure",
                                task.exception
                            )
                            makeToastLong(task.exception!!.message.toString())
                            binding.progressBar.visibility= View.GONE
                            binding.button.text="REGISTER"
                        }
                    }
            } else {
                makeToastLong("Password not matched")
                binding.progressBar.visibility= View.GONE
                binding.button.text="REGISTER"
            }
        }

    }
}