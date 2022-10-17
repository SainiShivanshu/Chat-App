package com.example.whatsappclone.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.whatsappclone.MainActivity
import com.example.whatsappclone.databinding.ActivityProfileUpdateBinding
import com.example.whatsappclone.model.UserModel
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*




class ProfileUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileUpdateBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage : FirebaseStorage
    private var imageUrl : Uri?=null
    private lateinit var database: FirebaseDatabase
private lateinit var user :String
    private var launchGalleryActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode== Activity.RESULT_OK){
            imageUrl=it.data!!.data
            binding.userImage.setImageURI(imageUrl)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        storage=FirebaseStorage.getInstance()

        loadUserInfo()
        binding.userImage.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchGalleryActivity.launch(intent)

        }

        binding.proceed.setOnClickListener {

            database.reference.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                user =  it.child("imageUrl").getValue().toString()

            if(binding.userName.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter your name",Toast.LENGTH_SHORT).show()
            }

            else UploadData()
        }
        }
    }



    private fun UploadData() {
        if(imageUrl !=null){
            val reference = storage.reference.child("Profile").child(Date().time!!.toString())
            reference.putFile(imageUrl!!).addOnCompleteListener{
                if(it.isSuccessful){
                    reference.downloadUrl.addOnSuccessListener {
                            task->
                        uploadInfo(task.toString())
                    }
                }
            }
        }

        else{
            upadatename()
        }

    }

    private fun upadatename() {
        val user = UserModel(
            auth.uid.toString(),
            binding.userName.text.toString(),
            auth.currentUser!!.phoneNumber.toString(),
             user
        )

        database.reference.child("users").child(auth.uid.toString()).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()


            }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(
            auth.uid.toString(),
            binding.userName.text.toString(),
            auth.currentUser!!.phoneNumber.toString(),
            imgUrl
        )

        database.reference.child("users").child(auth.uid.toString()).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()

            }

    }

    private fun loadUserInfo() {
        database.reference.child("users").child(auth.uid.toString()).get().addOnSuccessListener {

            binding.userName.setText(it.child("name").getValue().toString())
            binding.userNumber.setText(it.child("number").getValue().toString())
            Glide.with(this).load(it.child("imageUrl").getValue().toString()).into(binding.userImage)
        }
    }
}


