package com.webmobrilweatherapp.model.homepage

import com.google.gson.annotations.SerializedName

data class ProfilehomePageResponse(

	@field:SerializedName("post")
    val post: Post? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("user_detail")
	val userDetail: UserDetail? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class User(

	@field:SerializedName("verify_otp_timestamp")
	val verifyOtpTimestamp: String? = null,

	@field:SerializedName("city_lat")
	val cityLat: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

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
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: Any? = null,

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
	val selectButterfly: String? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

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

data class UserDetail(

	@field:SerializedName("verify_otp_timestamp")
	val verifyOtpTimestamp: String? = null,

	@field:SerializedName("city_lat")
	val cityLat: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

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
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: Any? = null,

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
	val selectButterfly: String? = null,

	@field:SerializedName("is_verify")
	val isVerify: Int? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

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

	@field:SerializedName("last_active")
	val lastActive: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Post(

	@field:SerializedName("first_page_url")
	val firstPageUrl: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("last_page")
	val lastPage: Int? = null,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String? = null,

	@field:SerializedName("next_page_url")
	val nextPageUrl: String? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)

data class DataItem(

	@field:SerializedName("likedbyme")
	var likedbyme: Int? = null,

	@field:SerializedName("is_active")
	val isActive: Int? = null,

	@field:SerializedName("post_video")
	val postVideo: Any? = null,

	@field:SerializedName("count")
	var count: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("likes_count")
	val likesCount: Int? = null,

	@field:SerializedName("post")
	val post: List<String?>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("post_comment_count")
	var postCommentCount: Int? = null,

	@field:SerializedName("post_type")
	val postType: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
)
