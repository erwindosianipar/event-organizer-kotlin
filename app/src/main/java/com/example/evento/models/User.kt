package com.example.evento.models

import com.google.gson.annotations.SerializedName

class User (
    var id:Int,
    var createdAt: String,
    var updateAt: String,
    var deletedAt: String,
    var email: String,
    var password: String,
    var name: String,
    var avatar: String,
    var role: String,
    var submission_status: String,
    var eventOrganizer: EventOrganizer
){}