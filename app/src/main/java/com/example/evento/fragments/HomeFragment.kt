package com.example.evento.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evento.*
import com.example.evento.adapter.EventAdapter
import com.example.evento.models.ResponseEvent
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

//    lateinit var listView: View
//    lateinit var arrayAdapter: EventArrayAdapter
//    var listEvent = mutableListOf<Event>()
    lateinit var eventAdapter:EventAdapter
    var listEvent:List<ResponseEvent>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val eventViewModel = activity?.run {
            ViewModelProviders.of(requireActivity()).get(EventViewModel::class.java)
        }

        eventViewModel?.events?.observe(this, Observer {
            events -> setEvents(events)
        })


//        initRecyclerView(view)
//        val eventApi = RepoEventOrganizerPostgresql.CreateUserService()
//
//        val eventViewModel = activity?.run {
//            ViewModelProviders.of(this).get(EventViewModel::class.java)
//        }
//
//        eventViewModel?.eventApi = eventApi
//
//        eventViewModel?.event?.observe(this, Observer {
//            event -> if (event == null) {
//            println("ERROR")
//        } else {
//            fetching(event)
//        }
//        })

        return view
    }

    private fun initRecyclerView(view: View) {
        view.recListEvent.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            eventAdapter = EventAdapter(activity!!.applicationContext, listEvent)
            adapter = eventAdapter
        }
        Log.d("INI EVENT LIST REC", listEvent?.size.toString())
    }

    private fun setEvents(events: List<ResponseEvent>?) {
        listEvent = events
        Log.d("INI EVENT LIST", listEvent?.get(0)?.name.toString())
        Log.d("INI EVENT LIST REC", listEvent?.size.toString())
        view!!.recListEvent.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            eventAdapter = EventAdapter(activity!!.applicationContext, listEvent)
            adapter = eventAdapter
        }
    }




//    fun fetching(event: List<Event>) {
//        arrayAdapter.clear()
//        for (i in 0 until event.size) {
//            arrayAdapter.add(event[i])
//        }
//    }


}