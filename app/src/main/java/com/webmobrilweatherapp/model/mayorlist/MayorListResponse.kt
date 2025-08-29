package com.webmobrilweatherapp.model.mayorlist

import com.google.gson.annotations.SerializedName

data class MayorListResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class Mayor(

	@field:SerializedName("meterologist_docs")
	val meterologistDocs: Any? = null,

	@field:SerializedName("city")
	val city: Any? = null,

	@field:SerializedName("is_verify")
	val isVerify: Any? = null,

	@field:SerializedName("about")
	val about: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Any? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: Long? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: Any? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

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

data class Data(

	@field:SerializedName("number_of_accurate_votes")
	val numberOfAccurateVotes: Int? = null,

	@field:SerializedName("vote_accuracy")
	val voteAccuracy: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("mayor")
	val mayor: Mayor? = null,

	@field:SerializedName("number_of_votes")
	val numberOfVotes: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("month_year")
	val monthYear: String? = null
)
