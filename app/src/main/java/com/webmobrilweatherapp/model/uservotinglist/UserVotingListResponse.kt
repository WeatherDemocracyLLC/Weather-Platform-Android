package com.webmobrilweatherapp.model.uservotinglist

import com.google.gson.annotations.SerializedName

data class UserVotingListResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Precipitation(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class User(

	@field:SerializedName("city_lat")
	val cityLat: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("about")
	val about: Any? = null,

	@field:SerializedName("city_code")
	val cityCode: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("device_type")
	val deviceType: Any? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Any? = null,

	@field:SerializedName("points")
	val points: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("city_long")
	val cityLong: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("meterologist_docs")
	val meterologistDocs: String? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("otp")
	val otp: Any? = null,

	@field:SerializedName("zipcode")
	val zipcode: String? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("min_weather")
	val minWeather: String? = null,

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null,

	@field:SerializedName("precipitation")
	val precipitation: Precipitation? = null,

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

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

	@field:SerializedName("max_weather")
	val maxWeather: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
)
