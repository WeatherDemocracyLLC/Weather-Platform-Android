package com.webmobrilweatherapp.model.metrologist.otp

import com.google.gson.annotations.SerializedName

data class MetrologistOtpResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
