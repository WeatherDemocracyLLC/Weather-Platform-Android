package com.webmobrilweatherapp.model.updatepost

import com.google.gson.annotations.SerializedName

data class UpdatePostResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
