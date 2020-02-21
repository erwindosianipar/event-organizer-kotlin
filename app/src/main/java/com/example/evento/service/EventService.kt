package com.example.evento.service

import com.example.evento.models.ResponseEvent
import com.example.evento.models.ResponseListEventAll
import retrofit2.Call
import retrofit2.http.GET

interface EventService{
    @GET("/event")
    fun getAllEvent(): Call<ResponseListEventAll>
}