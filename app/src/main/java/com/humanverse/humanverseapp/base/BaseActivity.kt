package com.humanverse.humanverseapp.base

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity() {
    fun makeToastLong(messsage: String){
        Toast.makeText(this,messsage, Toast.LENGTH_LONG).show()
    }
    fun makeToastShort(messsage: String){
        Toast.makeText(this,messsage, Toast.LENGTH_SHORT).show()
    }

}