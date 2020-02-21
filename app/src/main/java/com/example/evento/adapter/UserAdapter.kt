package com.example.evento.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evento.R
import com.example.evento.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_event.view.*

class UserAdapter (var context:Context,var user:List<User>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items:List<User> = user
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_event,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
        Log.d("SIZE",items.size.toString())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserViewHolder ->{
                holder.bind(items.get(position))
                holder.itemView.setOnClickListener{

                }
            }
        }
    }

    class UserViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.txtNameEvent
        val tgl:TextView = itemView.txtTglEvent

        fun bind(user:User){
            name.setText(name.text.toString())
            tgl.setText(name.text.toString())
        }
    }
}