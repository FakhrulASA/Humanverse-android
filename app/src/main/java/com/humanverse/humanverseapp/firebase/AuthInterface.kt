package com.humanverse.humanverseapp.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth

interface AuthInterface {
    fun firebaseLogin(username: String, password: String,auth: FirebaseAuth,context: Activity, onSuccess:()->Unit, onFailure:(String)->Unit)
    fun firebaseRegistration(username: String,password: String,auth: FirebaseAuth,context:Activity, onSuccess:()->Unit,onFailure:(String)->Unit)
}