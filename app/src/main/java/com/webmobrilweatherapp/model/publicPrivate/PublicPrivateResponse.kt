package com.webmobrilweatherapp.model.publicPrivate

import com.google.gson.annotations.SerializedName

data class PublicPrivateResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
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
	val deviceType: String? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Any? = null,

	@field:SerializedName("points")
	val points: String? = null,

	@field:SerializedName("profile_status")
	val profileStatus: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

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

	@field:SerializedName("is_approve")
	val isApprove: Int? = null,

	@field:SerializedName("meterologist_docs")
	val meterologistDocs: Any? = null,

	@field:SerializedName("select_butterfly")
	val selectButterfly: Any? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("zipcode")
	val zipcode: String? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("device_token")
	val deviceToken: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
