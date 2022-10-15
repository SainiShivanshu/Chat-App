package com.example.whatsappclone.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.DeleteLayoutBinding
import com.example.whatsappclone.databinding.ReceiverLayoutItemBinding
import com.example.whatsappclone.databinding.SentItemLayoutBinding
import com.example.whatsappclone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageAdapter(var context: Context,var list: ArrayList<MessageModel>,  senderRoom:String,
                     receiverRoom:String):RecyclerView.Adapter<RecyclerView.ViewHolder> (){

var ITEM_SENT = 1
    var ITEM_RECEIVE =2
    val senderRoom:String =""
    var receiverRoom:String=""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return if (viewType==ITEM_SENT)
          SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_item_layout,parent,false))

     else    ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false))
    }


    override fun getItemViewType(position: Int): Int {
return if(FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]

        if (holder.itemViewType==ITEM_SENT) {
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.userMsg.text = message.message




        }




//            viewHolder.itemView.setOnClickListener{
//
//                val view = LayoutInflater.from(context).inflate(R.layout.delete_layout,null)
//                val binding: DeleteLayoutBinding = DeleteLayoutBinding.bind(view)
//
//                val dialog  = AlertDialog.Builder(context)
//                    .setTitle("Delete Message")
//                    .setView(binding.root)
//                    .create()
//
//                binding.everyone.setOnClickListener {
//                    message.message="This message is deleted"
//                    message.message?.let{it1->
//                        FirebaseDatabase.getInstance().reference.child("chats")
//                            .child(senderRoom)
//                            .child("message")
//                            .child(it1).setValue(null).addOnSuccessListener {
//                               viewHolder.binding.userMsg.text = ""
//
//                            }
//                    }
//                    message.message.let {
//                            it1->
//
//                        FirebaseDatabase.getInstance().reference.child("chats")
//                            .child(receiverRoom)
//                            .child("message")
//                            .child(it1!!).removeValue()
//
//                    }
//
//                    dialog.dismiss()
//
//
//                }
//
//
//                binding.delete.setOnClickListener {
//                    message.message.let {
//                            it1->
//                        FirebaseDatabase.getInstance().reference.child("chats")
//                            .child(senderRoom)
//                            .child("message")
//                            .child(it1!!).setValue(null)
//                    }
//
//
//
//                    dialog.dismiss()
//                }
//
//                binding.cancel.setOnClickListener { dialog.dismiss() }
//                dialog.show()
//
//                false
//            }
//        }

        else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.userMsg.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class SentViewHolder(view:View) : RecyclerView.ViewHolder(view){
var binding=SentItemLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view:View) : RecyclerView.ViewHolder(view){
var binding = ReceiverLayoutItemBinding.bind(view)
    }

}