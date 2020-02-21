package com.example.evento

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.evento.models.ResponseEvent
import com.example.evento.models.ResponseListEventAll
import com.example.evento.models.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val users:MutableLiveData<UserList> by lazy{
        MutableLiveData<UserList>()
    }


    fun createUserService(id:String) {
        val getServiceUser = RepoEventOrganizerPostgresql.CreateUserService()
        getServiceUser.getUserById(id)
            .enqueue(object :
                Callback<UserList> {
                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    Log.e("tag", "errornya ${t.message}")
                }

                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    users.value = response.body()
//                    Log.d("Response",response.body()?.Users?.name)
                }

            })
    }


}