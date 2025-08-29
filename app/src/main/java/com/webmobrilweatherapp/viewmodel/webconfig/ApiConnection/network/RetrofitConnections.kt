package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network




import android.content.Context
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.webmobrilweatherapp.viewmodel.ApiInterface
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnections {

  private val clientService: ApiInterface? = null

  fun createService(): ApiInterface {
   var retrofit: Retrofit? = null
   if (retrofit == null) {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    val gson = GsonBuilder()
     .setLenient()
     .create()
    retrofit = Retrofit.Builder()
     .baseUrl(ApiConstants.BASE_URLS)
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

   private var connect: RetrofitConnections? = null

   @JvmStatic
   @get:Synchronized
   val instance: RetrofitConnections?
    get() {
     if (connect == null) {
      connect = RetrofitConnections()
     }
     return connect
    }
  }
 }