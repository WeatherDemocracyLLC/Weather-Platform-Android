package com.webmobrilweatherapp.adapters.userchat

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("send-message")
    fun sendMessageUser(
        @Field("receiver_id") receiver_id: String,
        @Field("message") message: String,
        @Field("type") type: String,
        @Field("message_time") message_time: String,
        @Header("authorization") token: String?
    ): Call<String>

    @Headers("Accept: application/json")
    @Multipart
    @POST("send-message")
    fun sendMessageImageuser(
        @Part("receiver_id") receiver_id: RequestBody,
        @Part message: MultipartBody.Part,
        @Part("type") type: RequestBody,
        @Part("message_time") message_time: String,
        @Header("authorization") token: String?
    ): Call<String>


}