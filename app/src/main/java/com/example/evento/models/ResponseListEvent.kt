package com.example.evento.models

import com.google.gson.annotations.SerializedName

class ResponseListEventAll (
    @SerializedName("data")
    var event:List<ResponseEvent>
){}