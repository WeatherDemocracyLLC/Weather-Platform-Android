package com.webmobrilweatherapp.model.getcomment

import com.google.gson.annotations.SerializedName

data class GetCommentResponse(

	@field:SerializedName("comment_count")
	val commentCount: String? = null,

	@field:SerializedName("like_couunt")
	val likeCouunt: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("comment")
	val comment: List<CommentItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CommentItem(

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("comment")
	var comment: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("parent_comment_id")
	val parentCommentId: Any? = null
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
	val phone: String? = null,

	@field:SerializedName("verify_otp")
	val verifyOtp: String? = null,

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
