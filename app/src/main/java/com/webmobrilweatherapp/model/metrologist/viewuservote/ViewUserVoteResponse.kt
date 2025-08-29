package com.webmobrilweatherapp.model.metrologist.viewuservote

import com.google.gson.annotations.SerializedName

data class ViewUserVoteResponse(

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

	@field:SerializedName("Today")
	val today: List<TodayItem?>? = null,

	@field:SerializedName("Day after Tomorrow")
	val dayAfterTomorrow: List<DayAfterTomorrowItem?>? = null,

	@field:SerializedName("Tomorrow")
	val tomorrow: List<TomorrowItem?>? = null
)

data class TodayItem(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("is_temp")
	val isTemp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("weathervoteid")
	val weathervoteid: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)
data class TomorrowItem(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("is_temp")
	val isTemp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("weathervoteid")
	val weathervoteid: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)

data class DayAfterTomorrowItem(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("is_temp")
	val isTemp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("weathervoteid")
	val weathervoteid: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)
