package com.webmobrilweatherapp.model.metrologist.forgot

import com.google.gson.annotations.SerializedName

data class MetrologistForgotResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("otp")
	val otp: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
