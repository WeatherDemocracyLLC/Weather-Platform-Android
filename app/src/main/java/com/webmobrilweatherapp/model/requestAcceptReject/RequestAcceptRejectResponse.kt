package com.webmobrilweatherapp.model.requestAcceptReject

import com.google.gson.annotations.SerializedName

data class RequestAcceptRejectResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
