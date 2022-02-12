package com.humanverse.humanverseapp.feature.auth.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityLoginBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.firebase.AuthImp
import com.humanverse.humanverseapp.util.ExtenFun.isNetworkAvailable
import com.humanverse.humanverseapp.util.hideDialog
import com.humanverse.humanverseapp.util.showConsent

//completed
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authImp: AuthImp
    @RequiresApi(Build.VERSION_CODES.M)
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
            if(this@LoginActivity.isNetworkAvailable()){
                verifyInputs()
            }else{
                binding.progressBar.visibility= View.GONE
            }

        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.textView2.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
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
            binding.button.text=getString(R.string.login_string)
        } else {
            if (binding.textLayoutPass.text!!.length < 6) {
                binding.textLayoutPass.error = "Please give valid password"
                binding.progressBar.visibility= View.GONE
                binding.button.text=getString(R.string.login_string)
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
                                binding.button.text=getString(R.string.login_string)
                                auth.signOut()
                            }

                        } else {
                            showConsent(task.exception?.message.toString(), this, true) {
                                hideDialog()
                            }
                            binding.progressBar.visibility= View.GONE
                            binding.button.text=getString(R.string.login_string)
                        }
                    }
            }
        }

    }
}