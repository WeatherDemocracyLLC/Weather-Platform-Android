package com.webmobrilweatherapp.beans.createnewpassword

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CreateNewPassResponse(

    @SerializedName("code")
    @Expose
    var code: Int,

    @SerializedName("error")
    @Expose
    val error: Boolean,

    @SerializedName("message")
    @Expose
    val message: String
)
