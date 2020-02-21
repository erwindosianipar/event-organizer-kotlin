package com.example.evento.models

import com.google.gson.annotations.SerializedName

class ResponseEvent (
    var ID :Int,
    var CreatedAt :String,
    var UpdateAt :String,
    var DeletedAt :String,
    var id_user :Int,
    var name :String,
    var lokasi :String,
    var event_date :String,
    var kuota :Int,
    var harga :Int,
    var banner: List<Banner>?
){}