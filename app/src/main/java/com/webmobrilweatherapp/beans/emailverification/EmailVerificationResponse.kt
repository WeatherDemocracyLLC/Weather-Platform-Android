package com.webmobrilweatherapp.beans.emailverification

import com.google.gson.annotations.SerializedName

data class EmailVerificationResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("otp")
	val otp: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
