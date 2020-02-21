package com.example.evento.models

import com.google.gson.annotations.SerializedName

class Banner (
    var id:Int,
    var createdAt: String,
    var updateAt: String,
    var deletedAt: String,
    var banner_foto: String,
    var EventRefer: Int
){}