package com.example.whatsappclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.bumptech.glide.Glide

import com.example.whatsappclone.adapter.MessageAdapter
import com.example.whatsappclone.databinding.ActivityChatBinding
import com.example.whatsappclone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

private lateinit var binding:ActivityChatBinding
private lateinit var database: FirebaseDatabase
private lateinit var senderUid:String
private lateinit var receiverUid:String

private lateinit var senderRoom:String
private lateinit var receiverRoom:String

private lateinit var list: ArrayList<MessageModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

list= ArrayList()


senderRoom = senderUid+receiverUid    // sender message dekhe toh receiver vale hi dikahi de
    receiverRoom = receiverUid+senderUid

         database=FirebaseDatabase.getInstance()

        binding.imageView4.setOnClickListener {
            finish()
        }
        val name = intent.getStringExtra("name")
        val profile = intent.getStringExtra("image")

        binding.name.text = name
        Glide.with(this@ChatActivity).load(profile).into(binding.profile01)


        binding.imageView2.setOnClickListener {
            if (binding.messageBox.text.isEmpty()){
                Toast.makeText(this,"Please enter your message",Toast.LENGTH_SHORT).show()

            }
          else{ val randomKey = database.reference.push().key
val message=MessageModel(randomKey,binding.messageBox.text.toString(),senderUid,Date().time)
                // unique key for storing data


                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!)
                    .setValue(message).addOnSuccessListener {

                        database.reference.child("chats").child(receiverRoom)
                            .child("message").child(randomKey!!).setValue(message).addOnSuccessListener {

                                binding.messageBox.text=null
                                Toast.makeText(this,"Message sent!!", Toast.LENGTH_SHORT).show()


                            }



                    }


            }

        }


        database.reference.child("chats").child(senderRoom).child("message").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (snapshot1 in snapshot.children){
                    val data = snapshot1.getValue(MessageModel::class.java)
                    list.add(data!!)
                }

                binding.recyclerView.adapter = MessageAdapter(this@ChatActivity,list,senderRoom,receiverRoom)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })





    }
}