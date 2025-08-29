package com.webmobrilweatherapp.model.Followers

import com.google.gson.annotations.SerializedName

data class FollowersResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("follower_list")
	val followerList: List<FollowerListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FollowerListItem(

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
