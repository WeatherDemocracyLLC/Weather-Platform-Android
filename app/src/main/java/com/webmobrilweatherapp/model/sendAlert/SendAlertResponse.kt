package com.webmobrilweatherapp.model.sendAlert

import com.google.gson.annotations.SerializedName

data class SendAlertResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
