package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network

import android.content.Context
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.webmobrilweatherapp.viewmodel.ApiInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnectionLocation {


    private val clientService: ApiInterface? = null

    fun createServiceeee(): ApiInterface {
        var retrofit: Retrofit? = null
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.LOCATION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
        return retrofit!!.create(ApiInterface::class.java)
    }

    companion object {
        fun callRetrofit(context: Context): Any {
            TODO("Not yet implemented")
        }

        private var connect: RetrofitConnectionLocation? = null

        @JvmStatic
        @get:Synchronized
        val instance: RetrofitConnectionLocation?
            get() {
                if (connect == null) {
                    connect = RetrofitConnectionLocation()
                }
                return connect
            }
    }
}