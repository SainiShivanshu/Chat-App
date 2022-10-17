package com.example.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.ChatActivity
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemProfileBinding
import com.example.chatapp.model.User
import com.squareup.picasso.Picasso


class UserAdapter(var context: Context, var userList: ArrayList<User>):
RecyclerView.Adapter<UserAdapter.UserViewHolder>()

{
inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

val binding :ItemProfileBinding=ItemProfileBinding.bind(itemView)

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       var v =LayoutInflater.from(context).inflate(R.layout.item_profile,
       parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.username.text=user.name


//
//        if (user.profileImage!= null) {
//            Glide.with(context).load(user.profileImage)
//                .placeholder(R.drawable.avatar)
//                .into(holder.binding.profile)
//        } else {
//            Glide.with(context).load(R.drawable.avatar)
//
//                .into(holder.binding.profile)
//        }


//        Glide.with(context)
//            .load(user.profileImage)
//  .transition(DrawableTransitionOptions.withCrossFade(400)) //Optional
//            .apply(
//                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
//                .error(R.drawable.avatar))
//            .into(holder.binding.profile);

        Picasso.get().load(user.profileImage).placeholder(R.drawable.avatar).into(holder.binding.profile);

        holder.itemView.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",user.name)
            intent.putExtra("image",user.profileImage)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = userList.size

}