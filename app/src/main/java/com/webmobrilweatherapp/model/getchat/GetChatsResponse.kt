package com.webmobrilweatherapp.model.getchat

import com.google.gson.annotations.SerializedName

data class GetChatsResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("is_read")
	val isRead: Int? = null,

	@field:SerializedName("message_time")
	val messageTime: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("message_type")
	val messageType: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
