package com.webmobrilweatherapp.model.accountdelete

import com.google.gson.annotations.SerializedName

data class DeleteAccountResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
