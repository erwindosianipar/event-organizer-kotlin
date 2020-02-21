package com.example.event_organizer.service

import com.example.evento.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("user/{id}")
    fun getUserById(@Path("id") id: String): Call<UserList>

    @POST("user/register")
    fun addUser(@Body userRegister: UserRegister): Call<User>

    @POST("user/login")
    fun getUserByEmail(@Body userLogin: UserLogin): Call<UserList>

    @Multipart
    @POST("event")
    fun addEvent(
        @Part("id_user") id: Int,
        @Part("name") name: RequestBody,
        @Part("lokasi") lokasi: RequestBody,
        @Part("event_date") event_date: RequestBody,
        @Part("kuota") kuota: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part banner_foto: MultipartBody.Part
    ): Call<ResponseCreateEvent>

    @GET("event")
    fun getAllEvent(): Call<ResponseListEvent>

    @Multipart
    @POST("/user/upgrade")
    fun upgradeUser(
        @Part("id") id: Int,
        @Part("name_eo") name_eo: RequestBody,
        @Part("ktp_number") ktp_number: RequestBody,
        @Part("siup_number") siup_number: RequestBody,
        @Part ktp_photo: MultipartBody.Part
    ): Call<ResponseUpgradeData>
}