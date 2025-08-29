package com.webmobrilweatherapp.model.metrologist.resetpassword

import com.google.gson.annotations.SerializedName

data class MetrologistResetPasswordResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
