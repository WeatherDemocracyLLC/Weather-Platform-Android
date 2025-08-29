package com.webmobrilweatherapp.model.postdelete

import com.google.gson.annotations.SerializedName

data class PostDeleteResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
