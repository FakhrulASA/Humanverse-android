package com.humanverse.humanverseapp.util

import android.app.Activity

object Utils {

    fun showAlertDialogForTap(
        context: Activity, title: String, description: String,
        ontapYes: () -> Unit, onTapNo: () -> Unit
    ) {
        showConsent(description, context, true) {
            ontapYes.invoke()
        }
    }
}