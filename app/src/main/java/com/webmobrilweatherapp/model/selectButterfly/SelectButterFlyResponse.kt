package com.webmobrilweatherapp.model.selectButterfly

import com.google.gson.annotations.SerializedName

data class SelectButterFlyResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
