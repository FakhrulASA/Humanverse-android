package com.humanverse.humanverseapp.base

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.humanverse.humanverseapp.util.hideDialog
import com.humanverse.humanverseapp.util.showConsent

abstract class BaseActivity : AppCompatActivity() {
    fun makeToastLong(messsage: String) {
        showConsent(messsage,this,true){

        }
    }

    fun makeToastShort(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_SHORT).show()
    }

    fun showAlertDialog(context: Context, title: String, description: String) {
        showConsent(description,this,true){
            hideDialog()
        }    }
}