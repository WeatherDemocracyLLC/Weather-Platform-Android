package com.webmobrilweatherapp.beans.termsprivacyresponse

import com.google.gson.annotations.SerializedName

data class Result(

    @SerializedName("terms_and_conditions")
    var termsAndConditions: String,

    @SerializedName("privacy_policy")
    val privacyPolicy: String
)
