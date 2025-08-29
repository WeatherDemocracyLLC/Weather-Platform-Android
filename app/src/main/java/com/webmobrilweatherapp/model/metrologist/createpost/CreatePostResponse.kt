package com.webmobrilweatherapp.model.metrologist.createpost

import com.google.gson.annotations.SerializedName

data class CreatePostResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
