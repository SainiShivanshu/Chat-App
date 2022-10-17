package com.example.chatapp

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chatapp.databinding.ActivityOTPBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.noobcode.otpview.OTPView
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    var binding :ActivityOTPBinding?=null
    var verificationId : String?=null
var auth:FirebaseAuth?=null
    var dialog :ProgressDialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOTPBinding.inflate(layoutInflater)

        setContentView(binding!!.root)
        dialog =ProgressDialog(this@OTPActivity)
        dialog!!.setMessage("Sending OTP...")
        dialog!!.setCancelable(false)
        dialog!!.show()
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        val phoneNumber = intent.getStringExtra("phoneNumber"  )
binding!!.phoneLble.text="Verify $phoneNumber"

        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this@OTPActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    TODO("Not yet implemented")
                }

                override fun onCodeSent(
                    verifyId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verifyId, forceResendingToken)
                    dialog!!.dismiss()
                    verificationId=verifyId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
                    binding!!.otpView.requestFocus()

                }

            }).build()
        val credential =   PhoneAuthProvider.verifyPhoneNumber(options)





        binding!!.otpView.setOTPListener(object : OTPView.OTPListener {
//                        override fun onOTPCompleted(otp: String) {
//                Toast.makeText(this@OTPActivity, "OTP Completed $otp", Toast.LENGTH_LONG).show()
//                //compare the OTP submitted by user to your generated one. Handle success and failure accordingly
//            }
            override fun onOTPCompleted(otp: String) {
                val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
                auth!!.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@OTPActivity, SetupProfileActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                           Toast.makeText(this@OTPActivity,"Failed",Toast.LENGTH_SHORT).show()

                        }
                    }

            }


        })





    }}



