package com.webmobrilweatherapp.model.butterflySpecies

import com.google.gson.annotations.SerializedName

data class ButterflySpeciesResponse(

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
	val butterfliesImage: String? = null,


	@field:SerializedName("is_selected")
	var is_selected: Boolean? = false
)
