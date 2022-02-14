package com.humanverse.humanverseapp.feature.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button13.setOnClickListener {
            finish()
        }
    }
}