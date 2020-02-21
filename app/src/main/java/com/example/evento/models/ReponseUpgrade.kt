package com.example.evento.models

import com.google.gson.annotations.SerializedName

class ReponseUpgrade (
    var id: Int,
    var created_at: String,
    var updated_at: String,
    var email: String,
    var name: String,
    var submission_status: String,
    @SerializedName("event_organizer")
    var event_organizer: EventOrganizer
){}