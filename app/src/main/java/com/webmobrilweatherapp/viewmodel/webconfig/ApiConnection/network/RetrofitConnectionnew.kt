package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network

 import com.webmobrilweatherapp.viewmodel.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConnectionNew {
 private const val BASE_URL = "https://weather338.p.rapidapi.com/"

 private val loggingInterceptor = HttpLoggingInterceptor().apply {
  level = HttpLoggingInterceptor.Level.BODY
 }

 private val httpClient = OkHttpClient.Builder()
  .addInterceptor(loggingInterceptor)
  .build()

 val instance: Retrofit by lazy {
  Retrofit.Builder()
   .baseUrl(BASE_URL)
   .client(httpClient)
   .addConverterFactory(GsonConverterFactory.create())
   .build()
 }

 fun createService(): ApiServices = instance.create(ApiServices::class.java)
}
