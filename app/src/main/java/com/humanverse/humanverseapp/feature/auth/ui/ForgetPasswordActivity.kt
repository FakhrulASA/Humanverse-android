package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.humanverse.humanverseapp.databinding.ActivityForgetPasswordBinding
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.hideDialog
import com.humanverse.humanverseapp.util.showConsent
import com.humanverse.humanverseapp.util.showLoader


class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            showLoader("Requesting reset password, please wait!",this)
            if (binding.textLayoutEmail.text.isNullOrEmpty()) {
                showConsent("Please enter your email, to reset password!", this, true) {
                    hideDialog()
                    hideConsent()
                }
            } else {
                auth.sendPasswordResetEmail(binding.textLayoutEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            hideConsent()
                            showConsent(
                                "Please check your email ${binding.textLayoutEmail.text.toString()} and reset your password with the link.",
                                this,
                                true
                            ) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                    }.addOnFailureListener {
                        showConsent(it.message.toString(), this, true) {
                            hideDialog()
                        }
                    }
            }

        }

    }
}