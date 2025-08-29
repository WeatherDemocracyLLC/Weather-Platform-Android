package com.webmobrilweatherapp.model.Following

import com.google.gson.annotations.SerializedName

data class FollowingResponse(

	@field:SerializedName("following_list")
	val followingList: List<FollowingListItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FollowingListItem(

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

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
