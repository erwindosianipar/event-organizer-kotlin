package com.example.evento.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Event(
    var id:Int,
    var createdAt: String,
    var updateAt: String,
    var deletedAt: String,
    val id_user: Int,
    var name: String,
    var lokasi: String,
    var event_date: String,
    var kuota: Int,
    var harga: Int,
    var banner: List<Banner>
): Serializable {}

class ResponseCreateEvent(
    var success: Boolean,
    var message: String,
    @SerializedName("data")
    var createEventResponse: Event
){}

class ResponseListEvent(
    var success: Boolean,
    var message: String,
    @SerializedName("data")
    var createEventResponse: List<Event>
){}