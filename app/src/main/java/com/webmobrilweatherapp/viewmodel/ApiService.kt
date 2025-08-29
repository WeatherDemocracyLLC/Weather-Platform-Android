package com.webmobrilweatherapp.viewmodel

import com.webmobrilweatherapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiServices {
    @GET("news/list?offset=0&limit=10")
    fun getNewUser(
        @Header("X-BingApis-SDK") sdk: String,
        @Header("X-RapidAPI-Key") rapidKey: String,
        @Header("RapidAPI-Host") newHost: String
    ): Call<NewsResponse>
}
