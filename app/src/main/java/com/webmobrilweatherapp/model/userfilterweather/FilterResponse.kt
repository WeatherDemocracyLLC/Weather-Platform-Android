package com.webmobrilweatherapp.model.userfilterweather

import com.google.gson.annotations.SerializedName

data class FilterResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("meterologist_docs")
	val meterologistDocs: Any? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("about")
	val about: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("otp")
	val otp: Any? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Any? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("weatherdate")
	val weatherdate: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_temp")
	val isTemp: Int? = null,

	@field:SerializedName("precipitation_id")
	val precipitationId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("temp_value")
	val tempValue: String? = null
)
