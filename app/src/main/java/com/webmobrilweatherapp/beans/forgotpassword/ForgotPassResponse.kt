package com.webmobrilweatherapp.beans.forgotpassword

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotPassResponse(

    @SerializedName("code")
    @Expose
    var code: Int,

    @SerializedName("error")
    @Expose
    val error: Boolean,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("otp")
    @Expose
    var otp: Int
)
