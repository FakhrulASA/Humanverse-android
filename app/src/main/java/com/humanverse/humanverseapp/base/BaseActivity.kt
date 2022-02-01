package com.humanverse.humanverseapp.base

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    fun makeToastLong(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_LONG).show()
    }

    fun makeToastShort(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_SHORT).show()
    }

    fun showAlertDialog(context: Context, title: String, description: String) {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(description)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }
}