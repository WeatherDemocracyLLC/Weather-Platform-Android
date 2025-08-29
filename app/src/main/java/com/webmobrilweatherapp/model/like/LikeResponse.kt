package com.webmobrilweatherapp.model.like

import com.google.gson.annotations.SerializedName

data class LikeResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("post_count")
	val post_count: Int? = null

)
