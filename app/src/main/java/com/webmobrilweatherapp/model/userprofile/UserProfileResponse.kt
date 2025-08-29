package com.webmobrilweatherapp.model.userprofile

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ButterfliesItem(

	@field:SerializedName("butterflies_title")
	val butterfliesTitle: String? = null,

	@field:SerializedName("butterflies_type")
	val butterfliesType: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sub_butterflies_type")
	val subButterfliesType: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("subbutterflies")
	val subbutterflies: List<SubbutterfliesItem?>? = null,

	@field:SerializedName("butterflies_image")
	val butterfliesImage: String? = null
)

data class Data(

	@field:SerializedName("city_lat")
	val cityLat: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("butterflies")
	val butterflies: List<ButterfliesItem?>? = null,

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
	val profileStatus: Int? = null,

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

	@field:SerializedName("is_approve")
	val isApprove: Int? = null,

	@field:SerializedName("meterologist_docs")
	val meterologistDocs: Any? = null,

	@field:SerializedName("followbyRequest")
	val followbyRequest: Int? = null,

	@field:SerializedName("select_butterfly")
	val selectButterfly: String? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("followbyloginuser")
	val followbyloginuser: Boolean? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("zipcode")
	val zipcode: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("followRequestdata")
	val followRequestdata: FollowRequestdata? = null,

	@field:SerializedName("location")
	val location: Any? = null,

	@field:SerializedName("last_active")
	val lastActive: String? = null,

	@field:SerializedName("post_count")
	val postCount: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SubbutterfliesItem(

	@field:SerializedName("butterflies_title")
	val butterfliesTitle: String? = null,

	@field:SerializedName("butterflies_type")
	val butterfliesType: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sub_butterflies_type")
	val subButterfliesType: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("butterflies_image")
	val butterfliesImage: String? = null
)

data class FollowRequestdata(

	@field:SerializedName("notification_type")
	val notificationType: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("notification_data")
	val notificationData: NotificationData? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("sender_id")
	val senderId: String? = null,

	@field:SerializedName("status")
	val status: Any? = null
)

data class NotificationData(

	@field:SerializedName("requestTo")
	val requestTo: String? = null,

	@field:SerializedName("requestFrom")
	val requestFrom: Int? = null
)
