package com.example.evento.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.example.evento.R
import com.example.evento.models.Event

class EventArrayAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int=0, var eventList: MutableList<Event>): ArrayAdapter<Event>(context, layoutRes, eventList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        val event = eventList.get(position)

        itemView?.findViewById<TextView>(R.id.txtEventName)?.setText(event.name!!.toString())
        itemView?.findViewById<TextView>(R.id.txtLocation)?.setText(event.lokasi!!.toString())

        return itemView
    }
}