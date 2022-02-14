package com.humanverse.humanverseapp.util

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.humanverse.humanverseapp.databinding.CommonConsentDialogFragmentBinding
import com.humanverse.humanverseapp.databinding.CommonLoaderBinding
import com.humanverse.humanverseapp.databinding.RatingLayoutBinding
import com.humanverse.humanverseapp.databinding.TrialDialogBinding
import com.humanverse.humanverseapp.feature.payment.PaymentActivity


private var dialog: Dialog? = null
private var dialog2: Dialog? = null

fun showConsent(type:String,activity: Activity, isHide :Boolean, succss:()->Unit) {
    try {
        val binding = CommonConsentDialogFragmentBinding.inflate(LayoutInflater.from(activity), null, false)
        dialog = Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(isHide)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            binding.aa.setOnClickListener {
                try {
                    succss.invoke()
                    dialog2?.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.tvTitle.text=type

            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun showTrialDialog(text:String, activity: Activity, succss:()->Unit) {
    try {
        val binding = TrialDialogBinding.inflate(LayoutInflater.from(activity), null, false)
        dialog = Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(true)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            binding.ratingBar.text = text
            binding.button12.setOnClickListener {
                try {
                    succss.invoke()
                    dialog?.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun showRatingDialog(activity: Activity, succss:()->Unit) {
    try {
        val binding = RatingLayoutBinding.inflate(LayoutInflater.from(activity), null, false)
        dialog = Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(true)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            binding.button12.setOnClickListener {
                try {
                    succss.invoke()
                    dialog2?.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun showLoader(type:String,activity: Activity) {
    try {
        val binding = CommonLoaderBinding.inflate(LayoutInflater.from(activity), null, false)
        dialog2 = Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            binding.tvTitle.text=type
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun hideConsent(){
    dialog2?.dismiss()
}
fun hideDialog(){
    dialog?.dismiss()
}