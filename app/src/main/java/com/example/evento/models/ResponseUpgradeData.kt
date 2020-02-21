package com.example.evento.models

import com.google.gson.annotations.SerializedName

class ResponseUpgradeData(
    @SerializedName("data")
    var userUpgradeResponse: ReponseUpgrade
){}