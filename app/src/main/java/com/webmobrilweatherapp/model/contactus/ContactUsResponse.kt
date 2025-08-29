package com.webmobrilweatherapp.model.contactus

import com.google.gson.annotations.SerializedName

data class ContactUsResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("contact")
	val contact: Contact? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Contact(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
