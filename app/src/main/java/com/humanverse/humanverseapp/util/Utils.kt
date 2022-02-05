package com.humanverse.humanverseapp.util

import android.app.AlertDialog
import android.content.Context

object Utils {

    fun showAlertDialogForTap(context: Context, title: String, description: String,
                              ontapYes:()->Unit, onTapNo:()->Unit) {
        val alertDialog: AlertDialog =
            AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(description)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK",
            { dialog, which ->
                ontapYes.invoke()
            })
        alertDialog.show()
    }
}