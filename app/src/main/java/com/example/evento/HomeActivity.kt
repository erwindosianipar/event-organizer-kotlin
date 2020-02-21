package com.example.evento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evento.adapter.EventArrayAdapter
import com.example.evento.models.Event
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONException

class HomeActivity : AppCompatActivity() {

//    var url = "http://10.0.2.2:8082/event"
//    lateinit var listView: ListView
//    lateinit var requestQueue: RequestQueue
//    var listEvent = mutableListOf<Event>()
//    lateinit var arrayAdapter: EventArrayAdapter

//    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        listView = findViewById(R.id.event_list)

//        id = intent.getIntExtra("ID", 0)
//        arrayAdapter = EventArrayAdapter(context = this, eventList = listEvent)
//        requestQueue = Volley.newRequestQueue(this)
//        listView.adapter = arrayAdapter

//        fetchAllEvent()



    }

//    private fun fetchAllEvent() {
//        val eventRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener{ response ->
//                resolveSuccess(response)
//            },
//            Response.ErrorListener { error: VolleyError? ->
//                Log.e("FETCH FAIL: ", error?.message.toString())
//            })
//        requestQueue.add(eventRequest)
//    }
//    private fun resolveSuccess(response: String?) {
//        try {
//            val arrayResponse = JSONArray(response)
//            for (i in 0 until arrayResponse.length()) {
//                println("TAMBAH DATA DISINI")
//            }
//        } catch (jsonEX: JSONException) {
//            Log.e("PARSE", jsonEX.message)
//        }
//    }
}
