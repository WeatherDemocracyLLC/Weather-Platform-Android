package com.webmobrilweatherapp.beans.verifyotp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(

    @SerializedName("code")
    @Expose
    var code: Int,
    @SerializedName("error")
    @Expose
    val error: Boolean,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("token")
    @Expose
    val token: String
)

