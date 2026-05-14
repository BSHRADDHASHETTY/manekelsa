package com.example.manekelsa.ui.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private var verificationId = ""

    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        onCodeSentSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(
                    credential: PhoneAuthCredential
                ) {

                    auth.signInWithCredential(credential)
                }

                override fun onVerificationFailed(
                    e: FirebaseException
                ) {

                    onFailure(
                        e.message ?: "Verification Failed"
                    )
                }

                override fun onCodeSent(
                    s: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {

                    super.onCodeSent(s, token)

                    verificationId = s

                    onCodeSentSuccess()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        if (verificationId.isEmpty()) {

            onFailure("Verification ID not found. Please resend OTP.")

            return
        }

        val credential = PhoneAuthProvider.getCredential(
            verificationId,
            otp
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    onSuccess()

                } else {

                    onFailure(
                        it.exception?.message ?: "Invalid OTP"
                    )
                }
            }
    }
}