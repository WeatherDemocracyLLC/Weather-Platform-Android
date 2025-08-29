package com.webmobrilweatherapp.beans.termsprivacyresponse

import com.google.gson.annotations.SerializedName

data class TermPrivacyResponse(

    @SerializedName("code")
    var code: Int,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val result: Result
)
