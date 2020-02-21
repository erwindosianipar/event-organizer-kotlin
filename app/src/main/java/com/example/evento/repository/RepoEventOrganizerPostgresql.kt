package com.example.event_organizer.repository

import com.example.event_organizer.service.UserService
import com.example.evento.service.EventService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepoEventOrganizerPostgresql {

    val BASE_URL_EVENT_ORGANIZER = "http://10.0.2.2:8082/"


    fun CreateUserService():UserService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_EVENT_ORGANIZER)
            .build()

        return retrofit.create(UserService::class.java)
    }

    fun CreateEventService():EventService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_EVENT_ORGANIZER)
            .build()

        return retrofit.create(EventService::class.java)
    }
}