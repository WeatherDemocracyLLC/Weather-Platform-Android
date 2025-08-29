package com.webmobrilweatherapp.model.metrologist.signupotpverification

import com.google.gson.annotations.SerializedName

data class MetrologistOtpVerificationResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
