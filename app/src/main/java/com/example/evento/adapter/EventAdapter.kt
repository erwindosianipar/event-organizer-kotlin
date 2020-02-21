package com.example.evento.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.evento.DetailEventActivity
import com.example.evento.R
import com.example.evento.models.ResponseEvent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_item.view.*

class EventAdapter(var context: Context, var event: List<ResponseEvent>?):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items:List<ResponseEvent>? = event

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHoler(
            LayoutInflater.from(parent.context).inflate(
                R.layout.event_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EventViewHoler->{
                holder.bind(items!!.get(position))
                holder.itemView.setOnClickListener {
                    holder.itemView.setOnClickListener{
                        val newIntent = Intent(context, DetailEventActivity::class.java).apply {
                            putExtra("name", items!![position].name)
                            putExtra("lokasi", items!![position].lokasi)
                            putExtra("event_date", items!![position].event_date)
                            putExtra("harga", items!![position].harga)
                            putExtra("kuota", items!![position].kuota)
                            putExtra("banner_foto", items!![position]!!.banner?.get(0)?.banner_foto)
                        }
                        context.startActivity(newIntent)
                    }
                }
            }
        }
    }

    class EventViewHoler constructor(
        itemView:View
    ):RecyclerView.ViewHolder(itemView){
        val nameEvent: TextView = itemView.txtEventName
        val lokasiEven: TextView = itemView.txtLocation
        val tglEvent: TextView = itemView.tglEvent
        val quotaEvent: TextView = itemView.txtKuota
        val priceEvent: TextView = itemView.txtPrice
        val imgEvent: ImageView = itemView.imageEvent

        fun bind(event:ResponseEvent){
            nameEvent.setText(event.name)
            lokasiEven.setText(event.lokasi)
            tglEvent.setText(event.event_date)
            quotaEvent.setText(event.kuota.toString())
            priceEvent.setText(event.harga.toString())
            if (event.banner?.get(0)?.banner_foto != null){
                Picasso.get().load("http://10.0.2.2:8083/"+event.banner?.get(0)?.banner_foto).into(imgEvent)
            }else{
                imgEvent.setImageResource(R.drawable.ic_whatshot_black_24dp)
                // Picasso.get().load(R.drawable.ic_whatshot_black_24dp).into(imgEvent)
            }
        }

    }

}