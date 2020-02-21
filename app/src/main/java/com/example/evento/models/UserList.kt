package com.example.evento.models

import com.google.gson.annotations.SerializedName

class UserList(
    @SerializedName("data")
    var Users: User
){}