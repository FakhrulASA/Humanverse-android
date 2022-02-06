package com.humanverse.humanverseapp.feature.helpcenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.humanverse.humanverseapp.base.BaseActivity
import com.humanverse.humanverseapp.databinding.ActivityHelpCenterBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.util.Utils.showAlertDialogForTap

class HelpCenterActivity : BaseActivity() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHelpCenterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        binding.button7.setOnClickListener {
            val helpcenter = hashMapOf(
                "subject" to binding.editTextTextPersonName.text.toString(),
                "problem description" to binding.editTextTextPersonName2.text.toString()
            )
            binding.progressBar4.visibility = View.VISIBLE
            if (binding.editTextTextPersonName.text.isNullOrEmpty() || binding.editTextTextPersonName2.text.isNullOrEmpty()) {
                showAlertDialog(this, "Field required", "Please insert valid information.")
                binding.progressBar4.visibility = View.GONE
            } else {
                db.collection("helpcenter")
                    .document(auth.currentUser!!.email.toString())
                    .set(helpcenter)
                    .addOnSuccessListener {
                        binding.progressBar4.visibility = View.GONE
                        showAlertDialogForTap(
                            this,
                            "Request submitted",
                            "Thank you for contacting us, we will contact you ASAP.",
                            {
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            },
                            {

                            })
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        binding.progressBar4.visibility = View.GONE
                    }

            }

        }

    }
}
