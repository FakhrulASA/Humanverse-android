package com.humanverse.humanverseapp.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.humanverse.humanverseapp.feature.auth.ui.RegistrationActivity

class AuthImp : AuthInterface {
    override fun firebaseLogin(
        username: String,
        password: String,
        auth: FirebaseAuth,
        context: Activity,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(username, password.trim())
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(this.toString(), "signInWithEmail:failure", task.exception)
                    onFailure.invoke(task.exception!!.message!!)
                }
            }
    }

    override fun firebaseRegistration(
        username: String,
        password: String,
        auth: FirebaseAuth,
        context: Activity,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(username, password.trim())
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        // Sign in success, update UI with the signed-in user's information
                        if (it.isSuccessful) {
                            Log.d(this.toString(), "createUserWithEmailAndPassword:success")
                            val user = auth.currentUser
                            context.startActivity(Intent(context, RegistrationActivity::class.java))
                        } else {
                            onFailure.invoke(it.exception!!.message!!)
                        }

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(this.toString(), "createUserWithEmailAndPassword:failure", task.exception)
                    onFailure.invoke(task.exception!!.message!!)
                }
            }
    }
}