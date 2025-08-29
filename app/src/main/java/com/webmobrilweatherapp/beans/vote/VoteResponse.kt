package com.webmobrilweatherapp.beans.vote

import com.google.gson.annotations.SerializedName

data class VoteResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
data class Data(

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("is_temp")
	val isTemp: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("precipitation_id")
	val precipitationId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)
