package com.webmobrilweatherapp.model.getimagepostuser

import com.google.gson.annotations.SerializedName

data class GetImagePostuserResponse(

	@field:SerializedName("post")
	val post: List<String?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
