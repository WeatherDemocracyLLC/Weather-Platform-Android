package com.webmobrilweatherapp.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("notifications")
	val notifications: List<NotificationsItem?>? = null
)

data class NotificationsItem(

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
	val requestFrom: Int? = null,

	@field:SerializedName("post_id")
	val postId: String? = null,

	@field:SerializedName("user_type")
    val userType: Int? = null,
)
