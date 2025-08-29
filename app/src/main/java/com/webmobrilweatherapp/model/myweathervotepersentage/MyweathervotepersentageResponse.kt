package com.webmobrilweatherapp.model.myweathervotepersentage

import com.google.gson.annotations.SerializedName

data class MyweathervotepersentageResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null,

	@field:SerializedName("votes")
	val votes: List<VotesItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class VotesItem(

	@field:SerializedName("precipitation")
	val precipitation: Precipitation? = null,

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("min_weather")
	val minWeather: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_temp")
	val isTemp: Int? = null,

	@field:SerializedName("vote_accurate")
	val voteAccurate: Int? = null,

	@field:SerializedName("precipitation_id")
	val precipitationId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("max_weather")
	val maxWeather: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)

data class Precipitation(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
