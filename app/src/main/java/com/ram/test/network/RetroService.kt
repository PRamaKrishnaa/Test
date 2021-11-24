package com.ram.test.network


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface RetroService {

    @GET("latest")
    fun getDataFromApi(): Call<JsonObject>
}