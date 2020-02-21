package com.example.evento

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.event_organizer.service.UserService
import com.example.evento.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {

//    val event: MutableLiveData<List<Event>> by lazy{
//        MutableLiveData<List<Event>>()
//    }
val events:MutableLiveData<List<ResponseEvent>> by lazy {
    MutableLiveData<List<ResponseEvent>>()
}

    fun createEventService(){
        val getServiceEvent = RepoEventOrganizerPostgresql.CreateEventService()
        getServiceEvent.getAllEvent().enqueue(object : Callback<ResponseListEventAll>{
            override fun onFailure(call: Call<ResponseListEventAll>, t: Throwable) {
                Log.e("tag", "errornya ${t.message}")
            }

            override fun onResponse(call: Call<ResponseListEventAll>, response: Response<ResponseListEventAll>) {
                events.value= response.body()?.event
                Log.d("Response",response.body()!!.event[0].name.toString())
            }

        })
    }
//    lateinit var eventApi: UserService
//
//    fun getAllEvent() {
//
//        eventApi.getAllEvent().enqueue(object : Callback<ResponseListEvent>{
//            override fun onFailure(call: Call<ResponseListEvent>, t: Throwable) {
//                Log.e("FETCH FAIL", t.message)
//            }
//
//            override fun onResponse(
//                call: Call<ResponseListEvent>,
//                response: Response<ResponseListEvent>
//            ) {
//                Log.i("FETCH DONE", response.body().toString())
//                var responseBody = response?.body()
//                event.value = responseBody?.createEventResponse
//            }
//        })
//    }
}