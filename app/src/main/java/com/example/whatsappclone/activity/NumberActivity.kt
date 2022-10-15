package com.example.whatsappclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsappclone.MainActivity
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
   private lateinit var binding :ActivityNumberBinding
private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

binding=ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

auth = FirebaseAuth.getInstance()

        if (auth.currentUser!=null){
            // if user is already login , he will be directed to Main Activity
            startActivity(Intent(this,MainActivity::class.java))
            finish()   // otherwise by back button he returns to number activity
        }

        binding.button.setOnClickListener{
            if (binding.phoneNumber.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Number!!",Toast.LENGTH_SHORT).show()

            }
            else{
                var intent =Intent(this,OTPActivity::class.java)
                intent.putExtra("number",binding.phoneNumber.text!!.toString())
                startActivity(intent)

            }


        }

    }
}