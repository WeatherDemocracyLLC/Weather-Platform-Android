package com.webmobrilweatherapp.model.comment

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("comment")
	val comment: Comment? = null,

	@field:SerializedName("post_count")
	val postCount: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Comment(

	@field:SerializedName("post_id")
	val postId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
